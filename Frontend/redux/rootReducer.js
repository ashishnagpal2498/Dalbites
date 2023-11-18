import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";
import RestaurantReducer from "./reducers/RestaurantReducer";
import UserReducer from "./reducers/UserReducer";
import OrderReducer from "./reducers/OrderReducer";
import RestaurantOrderReducer from "./reducers/RestaurantOrderReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  restaurant: RestaurantReducer,
  user: UserReducer,
  order: OrderReducer,
  restaurantOrder: RestaurantOrderReducer,
});

export default rootReducer;
