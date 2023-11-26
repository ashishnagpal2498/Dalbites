import {
  GET_USER_ORDER_SUCCESS,
  GET_USER_ORDER_FAILURE,
  ORDER_LOADING,
  POST_USER_ORDER_FAILURE,
  POST_USER_ORDER_SUCCESS,
  SET_SUCCESS_MESSAGE,
} from "../Types/OrderTypes";

const initialState = {
  orders: [],
  orderLoading: false,
  error: null,
  order: {},
  successMessage: null,
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case SET_SUCCESS_MESSAGE:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case ORDER_LOADING:
      return {
        ...state,
        ...payload,
      };
    case GET_USER_ORDER_SUCCESS:
    case POST_USER_ORDER_SUCCESS:
      return {
        ...state,
        ...payload,
        error: null,
      };
    case GET_USER_ORDER_FAILURE:
    case POST_USER_ORDER_FAILURE:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
