import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";
import RestaurantReducer from "./reducers/RestaurantReducer";
import UserReducer from "./reducers/UserReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  restaurant: RestaurantReducer,
  user: UserReducer,
});

export default rootReducer;
