import { put, call, takeEvery } from "redux-saga/effects";
import {
  getAllOrdersForRestaurantAPI, updateOrderStatusForRestaurantAPI
} from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import { GET_ORDER, GET_ORDER_FAILURE, GET_ORDER_SUCCESS, SET_ORDER_LOADING, UPDATE_ORDER_STATUS, UPDATE_ORDER_STATUS_FAILURE, UPDATE_ORDER_STATUS_SUCCESS } from "../Types/RestaurantOrderTypes";



function* getOrderSaga(action) {
  console.log("Order Saga",action.payload);
  const { payload } = action;
  try {
    console.log("Payload", payload);
    yield put({
      type: SET_ORDER_LOADING,
      payload: { restaurantOrderLoading: true },
    });
    const headers = {
      Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUmVzdGF1cmFudCIsImFkZHJlc3MiOm51bGwsInJvbGVfaWQiOjIsIm5hbWUiOiJEYXJzaGl0IiwiZXhwIjoxNzAyNzUxNDAwLCJpYXQiOjE3MDAyMzU2NzIsInVzZXJuYW1lIjoicnVzaGlwYXRlbDE0MDBAZ21haWwuY29tIn0.u0uVQPVRB9ooBOkOfHxIA9YVOjQMewl5Dungup1Wqpo`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, getAllOrdersForRestaurantAPI+payload, {
      headers: { ...headers },
    });
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_ORDER_SUCCESS,
        payload: { restaurantOrder: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_ORDER_FAILURE,
      payload: { restaurantOrderError: error.message },
    });
  } finally {
    yield put({
      type: SET_ORDER_LOADING,
      payload: { restaurantOrderLoading: false },
    });
  }
}

function* updateOrderStatusSaga(action) {
  console.log("update Order Saga",);
  const { payload } = action;
  try {
    console.log("Payload", payload,action);
    yield put({
      type: SET_ORDER_LOADING,
      payload: { restaurantOrderLoading: true },
    });
    const headers = {
      Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUmVzdGF1cmFudCIsImFkZHJlc3MiOm51bGwsInJvbGVfaWQiOjIsIm5hbWUiOiJEYXJzaGl0IiwiZXhwIjoxNzAyNzUxNDAwLCJpYXQiOjE3MDAyMzU2NzIsInVzZXJuYW1lIjoicnVzaGlwYXRlbDE0MDBAZ21haWwuY29tIn0.u0uVQPVRB9ooBOkOfHxIA9YVOjQMewl5Dungup1Wqpo`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.put, updateOrderStatusForRestaurantAPI+payload.id,payload.body, {
      headers: { ...headers },
    });
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: UPDATE_ORDER_STATUS_SUCCESS,
        payload: { restaurantOrder: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: UPDATE_ORDER_STATUS_FAILURE,
      payload: { restaurantOrderError: error.message },
    });
  } finally {
    yield put({
      type: SET_ORDER_LOADING,
      payload: { restaurantOrderLoading: false },
    });
  }
}


export function* RestaurantOrderSaga() {
  yield takeEvery(GET_ORDER, getOrderSaga);
  yield takeEvery(UPDATE_ORDER_STATUS,updateOrderStatusSaga)
}
