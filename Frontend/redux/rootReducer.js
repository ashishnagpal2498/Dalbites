import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";
import RestaurantReducer from "./reducers/RestaurantReducer";
import UserReducer from "./reducers/UserReducer";
import OrderReducer from "./reducers/OrderReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  restaurant: RestaurantReducer,
  user: UserReducer,
  order: OrderReducer,
});

export default rootReducer;
