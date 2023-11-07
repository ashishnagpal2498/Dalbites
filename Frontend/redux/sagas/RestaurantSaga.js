import { put, call, takeEvery } from "redux-saga/effects";
import {
  getAllBuildingsAPI,
  getRestaurantByIdAPI,
  viewAllRestaurantAPI,
  viewRestaurantMenuAPI,
} from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import {
  GET_BUILDING,
  GET_BUILDING_FAILURE,
  GET_BUILDING_SUCCESS,
  GET_RESTAURANT,
  GET_RESTAURANT_BY_ID,
  GET_RESTAURANT_BY_ID_FAILURE,
  GET_RESTAURANT_BY_ID_SUCCESS,
  GET_RESTAURANT_FAILURE,
  GET_RESTAURANT_MENU,
  GET_RESTAURANT_MENU_FAILURE,
  GET_RESTAURANT_MENU_SUCCESS,
  GET_RESTAURANT_SUCCESS,
  SET_RESTAURANT_LOADING,
} from "../Types/RestaurantTypes";

function* getRestaurantByIdSaga(action) {
  console.log("Restaurant by Id Saga");
  const { payload } = action;
  try {
    console.log("Payload", payload);
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });
    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${getRestaurantByIdAPI}/${payload.id}`,
      {
        headers: { ...headers },
      }
    );
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_BY_ID_SUCCESS,
        payload: { restaurant: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_BY_ID_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantsSaga(action) {
  console.log("Restaurant Saga");
  const { payload } = action;
  try {
    console.log("Payload", payload);
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });
    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, viewAllRestaurantAPI, {
      params: { id: payload.id.length > 0 ? payload.id.join(",") : "" },
      headers: { ...headers },
    });
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_SUCCESS,
        payload: { restaurants: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getBuildingSaga({ payload }) {
  console.log("Building Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, getAllBuildingsAPI, {
      headers: { ...headers },
    });
    console.log("Buildings", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_BUILDING_SUCCESS,
        payload: { buildings: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_BUILDING_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantMenuSaga({ payload }) {
  console.log("Get restaurant menu Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${viewRestaurantMenuAPI}/${payload.id}/menu`,
      {
        headers: { ...headers },
      }
    );
    console.log("Buildings", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENU_SUCCESS,
        payload: { restaurantMenu: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_MENU_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

export function* restaurantSaga() {
  yield takeEvery(GET_RESTAURANT, getRestaurantsSaga);
  yield takeEvery(GET_BUILDING, getBuildingSaga);
  yield takeEvery(GET_RESTAURANT_BY_ID, getRestaurantByIdSaga);
  yield takeEvery(GET_RESTAURANT_MENU, getRestaurantMenuSaga);
}
