import { GET_ORDER_FAILURE, GET_ORDER_SUCCESS, SET_ORDER_LOADING, UPDATE_ORDER_STATUS_FAILURE, UPDATE_ORDER_STATUS_SUCCESS } from "../Types/RestaurantOrderTypes";


const initialState = {
  restaurantOrder: [],
  restaurantOrderError: null,
  restaurantOrderLoading: false,
  // buildings: [],
  // selectedBuildings: [],
  // restaurant: {},
  // restaurantMenu: [{}],
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case SET_ORDER_LOADING:
      return {
        ...state,
        ...payload,
        restaurantOrderError: null,
      };
    case GET_ORDER_SUCCESS:
      return {
        ...state,
        ...payload,
        restaurantOrderError: null,
      };
    case GET_ORDER_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case UPDATE_ORDER_STATUS_SUCCESS:
     let updatedState= state.restaurantOrder.map(order => order.orderId !== payload.restaurantOrder.orderId ? order : payload.restaurantOrder);
     console.log(updatedState);
        return {
          ...state,
          ...{restaurantOrder:updatedState},
        restaurantOrderError: null,
        };
    case UPDATE_ORDER_STATUS_FAILURE:
     console.log(state,payload);

        return {
          ...state,
          ...payload,
        };
    default:
      return state;
  }
};
