import { put, call, takeEvery } from "redux-saga/effects";
import { postReviewAPI } from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import {
  POST_REVIEW,
  POST_REVIEW_FAILURE,
  POST_REVIEW_SUCCESS,
  PROFILE_LOADING,
} from "../Types/UserTypes";

function* postReviewSaga(action) {
  console.log("Post Review -->");
  const { payload } = action;
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
    const response = yield call(
      axios.post,
      postReviewAPI,
      {
        restaurantId: 4,
        rating: parseInt(payload.rating),
        reviewComment: payload.reviewComment,
      },
      { headers }
    );
    console.log("After API ");
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: POST_REVIEW_SUCCESS,
        payload: { review: response.data },
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

export function* userSaga() {
  yield takeEvery(POST_REVIEW, postReviewSaga);
}
