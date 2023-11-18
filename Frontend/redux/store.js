import { createStore, applyMiddleware } from "redux";
import createSagaMiddleware from "redux-saga";
import { all } from "redux-saga/effects";

import rootReducer from "./rootReducer"; // Import your root reducer here
import { authSaga } from "./sagas/AuthenticationSaga"; // Import your sagas
import { restaurantSaga } from "./sagas/RestaurantSaga";
import { userSaga } from "./sagas/UserSaga";
import { orderSaga } from "./sagas/OrderSaga";
import { RestaurantOrderSaga } from "./sagas/RestaurantOrderSaga";

const sagaMiddleware = createSagaMiddleware();

const store = createStore(rootReducer, applyMiddleware(sagaMiddleware));

function* rootSaga() {
  yield all([
    authSaga(),
    restaurantSaga(),
    userSaga(),
    orderSaga(),
    RestaurantOrderSaga(),
  ]);
}

sagaMiddleware.run(rootSaga);

export default store;
