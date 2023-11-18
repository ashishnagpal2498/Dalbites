import {
  POST_REVIEW_SUCCESS,
  POST_REVIEW_FAILURE,
  PROFILE_LOADING,
  GET_REVIEW_SUCCESS,
  GET_REVIEW_FAILURE,
  SET_SUCCESS_MESSAGE,
  GET_ALL_REVIEWS,
  GET_ALL_REVIEWS_FAILURE,
  GET_ALL_REVIEWS_SUCCESS,
  GET_USER_SUCCESS,
  GET_USER_FAILURE,
} from "../Types/UserTypes";

const initialState = {
  review: {},
  profileLoading: false,
  reviewError: null,
  successMessage: null,
  user: {},
  userReviews: [{}],
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
    case GET_ALL_REVIEWS_SUCCESS:
      return {
        ...state,
        ...payload,
        reviewError: null,
      };
    case GET_REVIEW_FAILURE:
    case GET_ALL_REVIEWS_FAILURE:
      return {
        ...state,
        ...payload,
      };
    case GET_USER_SUCCESS:
      return {
        ...state,
        ...payload,
        userError: null,
      };
    case GET_USER_FAILURE:
      return {
        ...state,
        ...payload,
      };
    default:
      return state;
  }
};
