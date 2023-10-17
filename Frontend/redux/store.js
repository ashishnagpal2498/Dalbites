import { createStore, applyMiddleware } from "redux";
import createSagaMiddleware from "redux-saga";
import { all } from "redux-saga/effects";

import rootReducer from "./rootReducer"; // Import your root reducer here
import { authSaga } from "./sagas/AuthenticationSaga"; // Import your sagas

const sagaMiddleware = createSagaMiddleware();

const store = createStore(rootReducer, applyMiddleware(sagaMiddleware));

function* rootSaga() {
  yield all([authSaga()]);
}

sagaMiddleware.run(rootSaga);

export default store;
