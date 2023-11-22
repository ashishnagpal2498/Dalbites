import {
  DELETE_RESTAURANT_MENUITEM,
  GET_BUILDING_FAILURE,
  GET_BUILDING_SUCCESS,
  GET_RESTAURANT_BY_ID_FAILURE,
  GET_RESTAURANT_BY_ID_SUCCESS,
  GET_RESTAURANT_FAILURE,
  GET_RESTAURANT_MENUS_FAILURE,
  GET_RESTAURANT_MENUS_SUCCESS,
  GET_RESTAURANT_MENU_FAILURE,
  GET_RESTAURANT_MENU_SUCCESS,
  GET_RESTAURANT_SUCCESS,
  SET_RESTAURANT_LOADING,
  SET_RESTAURANT_MENUITEM,
  SET_SELECTED_BUILDINGS,
  GET_RESTAURANT_REVIEW_SUCCESS,
  GET_RESTAURANT_REVIEW_FAILURE,
  UPDATE_RESTAURANT_MENUITEM
} from "../Types/RestaurantTypes";

const initialState = {
  restaurants: [],
  restaurantError: null,
  restaurantLoading: false,
  buildings: [],
  selectedBuildings: [],
  restaurant: {},
  restaurantMenu: [{}],
  restaurantReviews: [{}],
  restaurantMenus: [],
  cartItems: [],
  cartRestaurantId: null,
  cart: {},
  selectedRestaurantForCart: {}
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case SET_RESTAURANT_LOADING:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case SET_SELECTED_BUILDINGS:
      return {
        ...state,
        selectedBuildings: payload,
      };
    case GET_RESTAURANT_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case GET_RESTAURANT_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_BUILDING_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case GET_BUILDING_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_RESTAURANT_BY_ID_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case GET_RESTAURANT_BY_ID_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_RESTAURANT_MENU_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case GET_RESTAURANT_MENU_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_RESTAURANT_REVIEW_SUCCESS:
    case GET_RESTAURANT_MENUS_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantError: null,
      };
    case GET_RESTAURANT_REVIEW_FAILURE:
    case GET_RESTAURANT_MENUS_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case SET_RESTAURANT_MENUITEM:
      return {
        ...state,
        ...payload,
      };
    case UPDATE_RESTAURANT_MENUITEM:
      return {
        ...state,
        ...payload,
      };
    case DELETE_RESTAURANT_MENUITEM:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
