import { put, call, takeEvery } from "redux-saga/effects";
import {
  LOGIN_SUCCESS,
  LOGIN_FAILURE,
  SIGNUP_SUCCESS,
  SIGNUP_FAILURE,
  LOGOUT_SUCCESS,
  LOGOUT_FAILURE,
  SET_LOADING,
} from "../Types/AuthenticationTypes";
import { setLoading } from "../actions/Authentication";
import { LoginAPI, SignUpAPI } from "../APIs";
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
      yield put({ type: LOGIN_SUCCESS });
    } else {
      yield put({
        type: LOGIN_FAILURE,
        payload: { error: error.message, errorType: "Login" },
      });
    }
  } catch (error) {
    yield put({
      type: LOGIN_FAILURE,
      payload: { error: error.message, errorType: "Login" },
    });
  } finally {
    yield put(setLoading(false));
  }
}

function* logout() {
  try {
    yield put(setLoading(true));
    yield call(SecureStore.deleteItemAsync("token"));
    yield put({ type: LOGOUT_SUCCESS });
  } catch (error) {
    yield put({ type: LOGOUT_FAILURE, payload: { error: error.message } });
  } finally {
    yield put(setLoading(false));
  }
}

function* signUpSaga(action) {
  try {
    console.log("SignUp Saga -->");
    yield put({ type: SET_LOADING, payload: { loading: true } });

    console.log(action.payload);
    console.log("SignUp API --> ", SignUpAPI);
    const response = yield call(axios.post, SignUpAPI, action.payload, {
      headers: { ...API_HEADERS },
    });
    console.log("After API CALL");
    console.log(response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield call(SecureStore.setItemAsync, "token", response.data.token);
      yield put({ type: SIGNUP_SUCCESS });
    } else {
      yield put({
        type: SIGNUP_FAILURE,
        payload: { error: error.message, errorType: "Signup" },
      });
    }
  } catch (error) {
    console.log("Here in ERROR SAGA");
    console.log(error.message);
    yield put({
      type: SIGNUP_FAILURE,
      payload: { error: error.message, errorType: "Signup" },
    });
  } finally {
    yield put({ type: SET_LOADING, payload: { loading: false } });
  }
}

export function* authSaga() {
  yield takeEvery("LOGIN", loginSaga);
  yield takeEvery("SIGNUP", signUpSaga);
  yield takeEvery("LOGOUT", logout);
}
