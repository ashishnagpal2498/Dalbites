import { put, call, takeEvery, select, take } from "redux-saga/effects";

import {
  GET_CART_ITEM,
  ADD_CART_ITEM,
  DELETE_CART_ITEM,
  DELETE_CART_ITEMS,
  UPDATE_CART_ITEM,
  SELECT_RESTAURANT_FOR_CART,
  SET_RESTAURANT_CART,
  UPDATE_CART_TOTAL
} from "../Types/CartTypes";


function* getCartItemsSaga({ payload }) {
  try {
    yield put({
      type: GET_CART_ITEM,
      payload
    })
  } catch (error) {
    console.log('get cart item saga error ->', error)
  }
}

function* addCartItemSaga({ payload }) {
  try {
    if (payload.item) {
      const cartRestaurantId = yield select((state) => state.cart.restaurantId)
      const selectedRestaurant = yield select((state) => state.cart.selectedRestaurantForCart)
      if (cartRestaurantId != selectedRestaurant.id && cartRestaurantId != null) {
        yield put({
          type: DELETE_CART_ITEMS,
          payload: { cartItems: [] }
        })
      }
      yield put({
        type: SET_RESTAURANT_CART,
        payload: {
          id: selectedRestaurant.id
        }
      })
      let cartItems = yield select((state) => state.cart.cartItems) || []
      cartItems = cartItems.filter(item => item.id !== payload.item.id);
      cartItems.push(payload.item)
      yield put({
        type: ADD_CART_ITEM,
        payload: { cartItems }
      })
    }
  } catch (error) {
    console.log('add cart item saga error ->', error)
  }
}

function* deleteCartItemSaga({ payload }) {
  try {
    if (payload.id) {
      let cartItems = yield select((state) => state.cart.cartItems) || []
      cartItems = cartItems.filter(item => item.id !== payload.id);
      yield put({
        type: DELETE_CART_ITEM,
        payload: { cartItems }
      })
    }
  } catch (error) {
    console.log('delete cart item saga error ->', error)
  }
}

function* deleteCartItemsSaga({ payload }) {
  try {
    if (payload.item) {
      yield put({
        type: DELETE_CART_ITEMS,
        payload: { cartItems: [], selectedRestaurantForCart: null, subTotal: 0, maxWaitTime: 0 }
      })
    }
  } catch (error) {
    console.log('delete cart items saga error ->', error)
  }
}

function* updateCartItemSaga({ payload }) {
  try {
    if (payload.item) {
      let cartItems = yield select((state) => state.cart.cartItems) || []
      cartItems = cartItems.filter(item => item.id !== payload.item.id);
      cartItems.push(payload.item)
      yield put({
        type: UPDATE_CART_ITEM,
        payload: { cartItems }
      })
    }
  } catch (error) {
    console.log('update cart item saga error ->', error)
  }
}

function* selectRestaurantForCartSaga ({ payload }) {
  try {
    if (payload.id) {
      yield put({
        type: SELECT_RESTAURANT_FOR_CART,
        payload: { selectedRestaurantForCart: payload }
      })
    }
  } catch (error) {
    console.log('select restaurant for cart saga error ->', error)
  }
}

function* setRestaurantCartSaga ({ payload }) {
  try {
    if (payload.id) {
      yield put({
        type: SET_RESTAURANT_CART,
        payload: { restaurantId: payload.id }
      })
    }
  } catch (error) {
    console.log('set restaurant ID for cart saga error ->', error)
  }
}

function* updateCartTotalSaga ({ payload }) {
  try {
    if (!payload) {
      let total = 0;
      let maxPreparationTime = 0;
      const data = yield select((store) => store.cart.cartItems)
      data.forEach((item) => total += (+item.price * +item.quantity))
      if (data.length == 0) {
        maxPreparationTime = 0
      }
      data.forEach((item) => {
        if (+item.time > maxPreparationTime)
          maxPreparationTime = +item.time
      })
      yield put({
        type: UPDATE_CART_TOTAL,
        payload: { subTotal: total, maxPreparationTime }
      })
    }
  } catch (error) {
    console.log('select restaurant for cart saga error ->', error)
  }
}

export function* cartSaga() {
  yield takeEvery(GET_CART_ITEM, getCartItemsSaga);
  yield takeEvery(ADD_CART_ITEM, addCartItemSaga);
  yield takeEvery(DELETE_CART_ITEM, deleteCartItemSaga);
  yield takeEvery(DELETE_CART_ITEMS, deleteCartItemsSaga);
  yield takeEvery(UPDATE_CART_ITEM, updateCartItemSaga);
  yield takeEvery(SELECT_RESTAURANT_FOR_CART, selectRestaurantForCartSaga);
  yield takeEvery(SET_RESTAURANT_CART, setRestaurantCartSaga);
  yield takeEvery(UPDATE_CART_TOTAL, updateCartTotalSaga);
}
