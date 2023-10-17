import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
});

export default rootReducer;
