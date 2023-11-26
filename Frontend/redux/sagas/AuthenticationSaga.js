import { put, call, takeEvery } from "redux-saga/effects";
import {
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  SIGNUP_SUCCESS,
  SIGNUP_FAILURE,
  LOGOUT_SUCCESS,
  LOGOUT_FAILURE,
  SET_LOADING,
  SIGNUP,
  LOGIN,
  LOGOUT,
  VALIDATE_OTP,
  VALIDATE_OTP_SUCCESS,
  VALIDATE_OTP_FAILURE,
  FORGET_PASSWORD,
  FORGET_PASSWORD_SUCCESS,
  FORGET_PASSWORD_FAILURE,
  SETUP_RESTAURANT_ACCOUNT,
  SETUP_RESTAURANT_ACCOUNT_SUCCESS,
  SETUP_RESTAURANT_ACCOUNT_FAILURE,
} from "../Types/AuthenticationTypes";
import {
  LoginAPI,
  SetupRestaurantAccountAPI,
  SignUpAPI,
  forgetPasswordRequestAPI,
  forgetPasswordVerification,
  validateOTPAPI,
} from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import * as SecureStore from "expo-secure-store";
import axios from "axios";

function* loginSaga(action) {
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });
    const response = yield call(
      axios.post,
      LoginAPI,
      { username: action.payload.userId, password: action.payload.password },
      { headers: { ...API_HEADERS } }
    );
    if (response.status >= 200 && response.status <= 300) {
      yield call(SecureStore.setItemAsync, "token", response.data.token);
      const isRestaurant = response.data.role.toLowerCase() == "restaurant";
      if (isRestaurant) {
        yield call(
          SecureStore.setItemAsync,
          "restaurantId",
          response.data.restaurant_id.toString()
        );
        yield call(SecureStore.setItemAsync, "isRestaurant", "true");
      }
      yield put({
        type: LOGIN_SUCCESS,
        payload: {
          isRestaurant,
          redirect: isRestaurant ? "AddMenu" : "",
          restaurantId: response.data?.restaurant_id || "",
          token: response.data.token,
        },
      });
    } else {
      yield put({
        type: LOGIN_FAILURE,
        payload: { error: error.message, redirect: "Login" },
      });
    }
  } catch (error) {
    console.log(JSON.stringify(error));
    yield put({
      type: LOGIN_FAILURE,
      payload: {
        error: error.message,
        errorCode: error.code,
        redirect: "Login",
      },
    });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

function* logout() {
  console.log("Logout Saga");
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });
    yield call(SecureStore.deleteItemAsync, "token");
    yield call(SecureStore.deleteItemAsync, "restaurantId");
    yield call(SecureStore.deleteItemAsync, "isRestaurant");
    yield put({ type: LOGOUT_SUCCESS });
  } catch (error) {
    console.log("Error here- Logout");
    console.log(error);
    yield put({ type: LOGOUT_FAILURE, payload: { error: error.message } });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

function* signUpSaga(action) {
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });

    console.log(action.payload);
    const response = yield call(axios.post, SignUpAPI, action.payload, {
      headers: { ...API_HEADERS },
    });
    console.log("SignUp API Response ", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: SIGNUP_SUCCESS,
        payload: {
          tempToken: response.data.token,
          tempUser: {
            email: response.data.email,
            role: response.data.role,
          },
          redirect: "VerifyAccount",
        },
      });
    } else {
      yield put({
        type: SIGNUP_FAILURE,
        payload: { error: error.message, redirect: "Signup" },
      });
    }
  } catch (error) {
    if (error.response.status == 409) {
      yield put({
        type: SIGNUP_FAILURE,
        payload: { error: "User already exist, try login", redirect: "Signup" },
      });
    } else
      yield put({
        type: SIGNUP_FAILURE,
        payload: { error: error.message, redirect: "Signup" },
      });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

function* validateOTP(action) {
  console.log("ValidateOTP SAGA");
  const { payload } = action;
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });

    console.log(payload);

    const headers = {
      Authorization: `Bearer ${payload.tempToken}`,
      ...API_HEADERS,
    };
    console.log(headers);
    let response;
    if (payload.password) {
      response = yield call(axios.post, forgetPasswordVerification, payload, {
        headers: headers,
      });
    } else {
      response = yield call(axios.post, validateOTPAPI, payload, {
        headers: headers,
      });
    }

    if (response.status >= 200 && response.status <= 300) {
      console.log("Verify API Response ", response.data);
      if (!payload.password)
        yield call(SecureStore.setItemAsync, "token", response.data.token); // Token not coming yet
      yield put({
        type: VALIDATE_OTP_SUCCESS,
        payload: {
          successMessage: response.data.message,
          redirect: "VerifyAccount",
          token: response.data.token,
          ...payload,
        },
      });
    } else {
      yield put({
        type: VALIDATE_OTP_FAILURE,
        payload: {
          error: error ? error.message : "Invalid OTP Provided",
          redirect: "VerifyAccount",
        },
      });
    }
  } catch (error) {
    console.log("Here in ERROR SAGA");
    console.log(error.message);
    yield put({
      type: VALIDATE_OTP_FAILURE,
      payload: {
        error: "Invalid OTP Provided",
        redirect: "VerifyAccount",
      },
    });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

function* forgetPassword(action) {
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });

    console.log(action.payload);
    const response = yield call(
      axios.post,
      forgetPasswordRequestAPI,
      action.payload,
      {
        headers: { ...API_HEADERS },
      }
    );
    console.log("forget Password Request API Response ", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: FORGET_PASSWORD_SUCCESS,
        payload: {
          tempToken: response.data.token,
          tempUser: {
            email: response.data.email,
            forgetPassword: true,
          },
          redirect: "VerifyAccount",
        },
      });
    } else {
      yield put({
        type: FORGET_PASSWORD_FAILURE,
        payload: {
          error: error.message,
          redirect: "ForgetPassword",
        },
      });
    }
  } catch (error) {
    console.log("Here in ERROR SAGA");
    console.log(error.message);
    yield put({
      type: FORGET_PASSWORD_FAILURE,
      payload: { error: "B00 Id doesn't exist", redirect: "ForgetPassword" },
    });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

function* setupRestaurantAccountSaga(action) {
  try {
    yield put({ type: SET_LOADING, payload: { loading: true } });

    let formdata = new FormData();

    formdata.append("name", action.payload.restaurantName);
    formdata.append("description", action.payload.restaurantDescription);
    formdata.append("building", action.payload.buildingId);
    formdata.append("deliveryTime", action.payload.estimatedDeliveryTime);
    formdata.append("file", {
      uri: action.payload.restaurantImage.uri,
      name: "image.jpg",
      type: "image/jpg",
    });

    const response = yield call(
      axios.post,
      SetupRestaurantAccountAPI,
      formdata,
      {
        headers: {
          Authorization: `Bearer ${action.payload.authToken}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: SETUP_RESTAURANT_ACCOUNT_SUCCESS,
        payload: {
          redirect: "SetupRestaurantScreen",
          restaurantId: response.data.id,
        },
      });
    } else {
      yield put({
        type: SETUP_RESTAURANT_ACCOUNT_FAILURE,
        payload: { error: error.message, redirect: "SetupRestaurantScreen" },
      });
    }
  } catch (error) {
    if (error.response.status == 409) {
      yield put({
        type: SETUP_RESTAURANT_ACCOUNT_FAILURE,
        payload: {
          error: "Some error occured, try login",
          redirect: "SetupRestaurantScreen",
        },
      });
    } else
      yield put({
        type: SETUP_RESTAURANT_ACCOUNT_FAILURE,
        payload: { error: error.message, redirect: "SetupRestaurantScreen" },
      });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

export function* authSaga() {
  yield takeEvery(LOGIN, loginSaga);
  yield takeEvery(SIGNUP, signUpSaga);
  yield takeEvery(LOGOUT, logout);
  yield takeEvery(VALIDATE_OTP, validateOTP);
  yield takeEvery(FORGET_PASSWORD, forgetPassword);
  yield takeEvery(SETUP_RESTAURANT_ACCOUNT, setupRestaurantAccountSaga);
}
