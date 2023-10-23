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
  VALIDATE_OTP_SUCCESS,
  VALIDATE_OTP_FAILURE,
  SET_SUCCESS_MESSAGE,
  FORGET_PASSWORD_SUCCESS,
  FORGET_PASSWORD_FAILURE,
} from "../Types/AuthenticationTypes";

const initialState = {
  loading: true,
  isAuth: false,
  error: null,
  tempToken: null,
  tempUser: {},
  user: {},
  successMessage: "",
  redirect: "",
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
        ...payload,
        error: null,
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
        error: null,
        redirect: "",
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
      };
    case VALIDATE_OTP_SUCCESS:
      return {
        ...state,
        error: null,
        loading: false,
        ...payload,
      };
    case VALIDATE_OTP_FAILURE:
      return {
        ...state,
        loading: false,
        isAuth: false,
        ...payload,
      };
    case SET_SUCCESS_MESSAGE:
      return {
        ...state,
        ...payload,
        tempToken: null,
        tempUser: {},
      };
    case FORGET_PASSWORD_SUCCESS:
      return {
        ...state,
        error: null,
        loading: false,
        ...payload,
      };
    case FORGET_PASSWORD_FAILURE:
      return {
        ...state,
        loading: false,
        isAuth: false,
        ...payload,
      };
    default:
      return state;
  }
};
