import { put, call, takeEvery } from "redux-saga/effects";
import { getUserOrderAPI } from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import {
  GET_USER_ORDER_FAILURE,
  GET_USER_ORDER_SUCCESS,
  ORDER_LOADING,
  GET_USER_ORDER,
} from "../Types/OrderTypes";

function* getUserOrderSaga({ payload }) {
  console.log("User Order Saga");
  try {
    yield put({
      type: ORDER_LOADING,
      payload: { orderLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, getUserOrderAPI, {
      headers,
    });
    console.log("User Order History ", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_USER_ORDER_SUCCESS,
        payload: { orders: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_USER_ORDER_FAILURE,
      payload: { error: error.message },
    });
  } finally {
    yield put({
      type: ORDER_LOADING,
      payload: { orderLoading: false },
    });
  }
}

export function* orderSaga() {
  yield takeEvery(GET_USER_ORDER, getUserOrderSaga);
}
