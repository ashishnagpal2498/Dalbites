import { combineReducers } from "redux";
import AuthenticationReducer from "./reducers/AuthenticationReducer";
import RestaurantReducer from "./reducers/RestaurantReducer";
import UserReducer from "./reducers/UserReducer";
import OrderReducer from "./reducers/OrderReducer";
import CartReducer from "./reducers/CartReducer";

const rootReducer = combineReducers({
  authentication: AuthenticationReducer,
  restaurant: RestaurantReducer,
  user: UserReducer,
  order: OrderReducer,
  cart: CartReducer
});

export default rootReducer;
