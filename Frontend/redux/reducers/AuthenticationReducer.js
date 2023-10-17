import {
  LOGIN_FAILURE,
  LOGIN_SUCCESS,
  SET_LOADING,
  SIGNUP_FAILURE,
  SIGNUP_SUCCESS,
  LOGOUT_SUCCESS,
  LOGOUT_FAILURE,
  SET_TOKEN,
  SET_ERROR,
} from "../Types/AuthenticationTypes";

const initialState = {
  loading: true,
  isAuth: false,
  error: null,
  errorType: null,
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case SET_LOADING:
      return {
        ...state,
        loading: payload.loading,
      };
    case SET_TOKEN:
      return {
        ...state,
        loading: false,
        isAuth: true,
      };
    case SET_ERROR:
      return {
        ...state,
        ...payload,
      };
    case SIGNUP_SUCCESS:
      return {
        ...state,
        authError: null,
        isAuth: true,
      };
    case SIGNUP_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case LOGIN_SUCCESS:
      return {
        ...state,
        loading: false,
        isAuth: true,
      };
    case LOGIN_FAILURE:
      return {
        ...state,
        isAuth: false,
        ...payload,
      };
    case LOGOUT_SUCCESS:
      return {
        ...state,
        loading: false,
        isAuth: false,
      };
    case LOGOUT_FAILURE:
      return {
        ...state,
        loading: false,
        isAuth: true,
        authError: payload.error,
      };
    default:
      return state;
  }
};
