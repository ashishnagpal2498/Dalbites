import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";
import RestaurantReducer from "./reducers/RestaurantReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  restaurant: RestaurantReducer,
});

export default rootReducer;
