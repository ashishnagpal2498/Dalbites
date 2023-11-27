import {
  ADD_CART_ITEM,
  GET_CART_ITEM,
  DELETE_CART_ITEM,
  DELETE_CART_ITEMS,
  UPDATE_CART_ITEM,
  SELECT_RESTAURANT_FOR_CART,
  SET_RESTAURANT_CART,
  UPDATE_CART_TOTAL
} from "../Types/CartTypes";

const initialState = {
  cartItems: [],
  restaurantId: null,
  selectedRestaurantForCart: {},
  subTotal: 0,
  maxPreparationTime: 0
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case GET_CART_ITEM:
      return {
        ...state,
        ...payload
      };
    case ADD_CART_ITEM:
      return {
        ...state,
        ...payload
      };
    case DELETE_CART_ITEM:
      return {
        ...state,
        ...payload
      };
    case UPDATE_CART_ITEM:
      return {
        ...state,
        ...payload
      };
    case DELETE_CART_ITEMS:
      return {
        ...state,
        ...payload
      };
    case SELECT_RESTAURANT_FOR_CART:
      return {
        ...state,
        ...payload
      }
    case SET_RESTAURANT_CART:
      return {
        ...state,
        ...payload
      }
    case UPDATE_CART_TOTAL:
      return {
        ...state,
        ...payload
      }
    default:
      return state;
  }
};
