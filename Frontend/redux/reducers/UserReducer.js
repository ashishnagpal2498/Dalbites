import {
  POST_REVIEW_SUCCESS,
  POST_REVIEW_FAILURE,
  PROFILE_LOADING,
  GET_REVIEW_SUCCESS,
  GET_REVIEW_FAILURE,
  SET_SUCCESS_MESSAGE,
} from "../Types/UserTypes";

const initialState = {
  review: {},
  profileLoading: false,
  reviewError: null,
  successMessage: null,
};

export default (state = initialState, action) => {
  const { type, payload } = action;
  switch (type) {
    case PROFILE_LOADING:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case SET_SUCCESS_MESSAGE:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case POST_REVIEW_SUCCESS:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case POST_REVIEW_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_REVIEW_SUCCESS:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case GET_REVIEW_FAILURE:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
