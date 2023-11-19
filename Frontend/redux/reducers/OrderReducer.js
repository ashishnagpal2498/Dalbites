import {
  GET_USER_ORDER_SUCCESS,
  GET_USER_ORDER_FAILURE,
  ORDER_LOADING,
} from "../Types/OrderTypes";

const initialState = {
  orders: [],
  orderLoading: false,
  error: null,
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case ORDER_LOADING:
      return {
        ...state,
        ...payload,
      };
    case GET_USER_ORDER_SUCCESS:
      return {
        ...state,
        ...payload,
        error: null,
      };
    case GET_USER_ORDER_FAILURE:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
