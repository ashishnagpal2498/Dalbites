import { put, call, takeEvery } from "redux-saga/effects";
import { getReviewAPI, postReviewAPI, updateReviewAPI } from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import {
  GET_REVIEW,
  GET_REVIEW_FAILURE,
  GET_REVIEW_SUCCESS,
  POST_REVIEW,
  POST_REVIEW_FAILURE,
  POST_REVIEW_SUCCESS,
  PROFILE_LOADING,
} from "../Types/UserTypes";

function* postOrUpdateReviewSaga({ payload }) {
  console.log("Post or Update Review -->");
  try {
    console.log("Payload", payload);
    yield put({
      type: PROFILE_LOADING,
      payload: { profileLoading: true },
    });
    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    let response;
    if (payload.review) {
      response = yield call(
        axios.put,
        updateReviewAPI,
        {
          reviewId: payload.review.reviewId,
          rating: parseInt(payload.rating),
          reviewComment: payload.reviewComment,
        },
        { headers }
      );
    } else {
      response = yield call(
        axios.post,
        postReviewAPI,
        {
          restaurantId,
          rating: parseInt(payload.rating),
          reviewComment: payload.reviewComment,
        },
        { headers }
      );
    }
    console.log("After API ");
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: POST_REVIEW_SUCCESS,
        payload: {
          review: response.data,
          successMessage:
            "Review Submitted - You will be redirected in 3 seconds",
        },
      });
      console.log("Response from API --> ", response.data);
    }
    console.log("API hit --> ");
  } catch (error) {
    yield put({
      type: POST_REVIEW_FAILURE,
      payload: { reviewError: error.message },
    });
  } finally {
    yield put({
      type: PROFILE_LOADING,
      payload: { profileLoading: false },
    });
  }
}

function* getReviewSaga({ payload }) {
  console.log("Get review Saga");
  try {
    yield put({
      type: PROFILE_LOADING,
      payload: { profileLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${getReviewAPI}/${payload.id}/user`,
      {
        headers,
      }
    );
    console.log("Review", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_REVIEW_SUCCESS,
        payload: { review: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_REVIEW_FAILURE,
      payload: { reviewError: error.message },
    });
  } finally {
    yield put({
      type: PROFILE_LOADING,
      payload: { profileLoading: false },
    });
  }
}

export function* userSaga() {
  yield takeEvery(POST_REVIEW, postOrUpdateReviewSaga);
  yield takeEvery(GET_REVIEW, getReviewSaga);
}
