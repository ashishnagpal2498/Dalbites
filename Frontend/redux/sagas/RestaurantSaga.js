import { put, call, takeEvery, take, takeLatest, select } from "redux-saga/effects";
import {
  getAllBuildingsAPI,
  getRestaurantByIdAPI,
  getRestaurantReviewAPI,
  viewAllRestaurantAPI,
  viewRestaurantMenuAPI,
  getRestaurantMenuAPI,
  addRestaurantMenuItemAPI,
  updateRestaurantMenuItemAPI,
  deleteRestaurantMenuItemAPI,
} from "../APIs";
import { API_HEADERS } from "../../src/Utils/Api-Cred";
import axios from "axios";
import {
  DELETE_RESTAURANT_MENUITEM,
  GET_BUILDING,
  GET_BUILDING_FAILURE,
  GET_BUILDING_SUCCESS,
  GET_RESTAURANT,
  GET_RESTAURANT_BY_ID,
  GET_RESTAURANT_BY_ID_FAILURE,
  GET_RESTAURANT_BY_ID_SUCCESS,
  GET_RESTAURANT_FAILURE,
  GET_RESTAURANT_MENU,
  GET_RESTAURANT_MENUS,
  GET_RESTAURANT_MENUS_FAILURE,
  GET_RESTAURANT_MENUS_SUCCESS,
  GET_RESTAURANT_MENU_FAILURE,
  GET_RESTAURANT_MENU_SUCCESS,
  GET_RESTAURANT_REVIEW,
  GET_RESTAURANT_REVIEW_FAILURE,
  GET_RESTAURANT_REVIEW_SUCCESS,
  GET_RESTAURANT_SUCCESS,
  SET_RESTAURANT_LOADING,
  SET_RESTAURANT_MENUITEM,
  UPDATE_RESTAURANT_MENUITEM,
  GET_CART_ITEM,
  ADD_CART_ITEM,
  DELETE_CART_ITEM,
  DELETE_CART_ITEMS,
  UPDATE_CART_ITEM,
  SELECT_RESTAURANT_FOR_CART
} from "../Types/RestaurantTypes";

function* getRestaurantByIdSaga(action) {
  console.log("Restaurant by Id Saga");
  const { payload } = action;
  try {
    console.log("Payload", payload);
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });
    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${getRestaurantByIdAPI}/${payload.id}`,
      {
        headers: { ...headers },
      }
    );
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_BY_ID_SUCCESS,
        payload: { restaurant: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_BY_ID_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantsSaga(action) {
  console.log("Restaurant Saga");
  const { payload } = action;
  try {
    console.log("Payload", payload);
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });
    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, viewAllRestaurantAPI, {
      params: { id: payload.id.length > 0 ? payload.id.join(",") : "" },
      headers: { ...headers },
    });
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_SUCCESS,
        payload: { restaurants: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getBuildingSaga({ payload }) {
  console.log("Building Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(axios.get, getAllBuildingsAPI, {
      headers: { ...headers },
    });
    console.log("Buildings", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_BUILDING_SUCCESS,
        payload: { buildings: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_BUILDING_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantMenuSaga({ payload }) {
  console.log("Get restaurant menu Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${viewRestaurantMenuAPI}/${payload.id}/menu`,
      {
        headers: { ...headers },
      }
    );
    console.log("Buildings", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENU_SUCCESS,
        payload: { restaurantMenu: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_MENU_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantReviewSaga({ payload }) {
  console.log("Get restaurant review Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    const response = yield call(
      axios.get,
      `${getRestaurantReviewAPI}/${payload.id}`,
      {
        headers: { ...headers },
      }
    );
    console.log("Reviews --> ", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_REVIEW_SUCCESS,
        payload: { restaurantReviews: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_REVIEW_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* getRestaurantMenu({ payload }) {
  console.log("Get restaurant menu Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);
    const response = yield call(
      axios.get,
      `${getRestaurantMenuAPI}/${payload.id}/menu`,
      {
        headers: { ...headers },
      }
    );
    console.log("Restaurant Menu", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENUS_SUCCESS,
        payload: { restaurantMenus: response.data },
      });
    }
  } catch (error) {
    yield put({
      type: GET_RESTAURANT_MENUS_FAILURE,
      payload: { restaurantError: error.message },
    });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* addRestaurantMenuItem({ payload }) {
  console.log("Add restaurant menu item Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      "Content-Type": "multipart/form-data",
    };
    console.log("Headers - ", headers);
    let is_available = payload.is_available == true ? "1" : "0";
    let formData = new FormData();
    formData.append("name", payload.name);
    formData.append("description", payload.description);
    formData.append("price", payload.price);
    formData.append("time", payload.time);
    formData.append("isAvailable", is_available);
    formData.append("restaurantId", payload.restaurant_id);
    formData.append("file", payload.fileObj);

    const response = yield call(
      axios.post,
      `${addRestaurantMenuItemAPI}/${payload.restaurant_id}/add-menu-item`,
      formData,
      {
        headers: { ...headers },
      }
    );
    console.log("Add restaurant menu item", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENUS_SUCCESS,
        payload: { restaurantMenus: response.data, restaurantLoading: false },
      });
    }
  } catch (error) {
    return {
      type: SET_RESTAURANT_MENUITEM,
      payload: { restaurantError: error.message },
    };
    // yield put({
    //   type: SET_RESTAURANT_MENUITEM,
    //   payload: { restaurantError: error.message },
    // });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* updateRestaurantMenuItem({ payload }) {
  console.log("Update restaurant menu item Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);

    const response = yield call(
      axios.put,
      `${updateRestaurantMenuItemAPI}/${payload.restaurant_id}/update-menu-item`,
      {
        id: payload.id,
        name: payload.name,
        description: payload.description,
        price: payload.price,
        time: payload.time,
        is_available: payload.is_available,
      },
      {
        headers: { ...headers },
      }
    );
    console.log("Update restaurant menu item", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENUS_SUCCESS,
        payload: { restaurantMenus: response.data, restaurantLoading: false },
      });
    }
  } catch (error) {
    return {
      type: SET_RESTAURANT_MENUITEM,
      payload: { restaurantError: error.message },
    };
    // yield put({
    //   type: SET_RESTAURANT_MENUITEM,
    //   payload: { restaurantError: error.message },
    // });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

function* deleteRestaurantMenuItem({ payload }) {
  console.log("Delete restaurant menu item Saga");
  try {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: true },
    });

    const headers = {
      Authorization: `Bearer ${payload.token}`,
      ...API_HEADERS,
    };
    console.log("Headers - ", headers);

    let restaurantId = parseInt(payload.restaurantId);

    const response = yield call(
      axios.delete,
      `${deleteRestaurantMenuItemAPI}/${restaurantId}/delete-menu-item/${payload.menuId}`,
      {
        headers: { ...headers },
      }
    );
    console.log("Delete restaurant menu item", response.data);
    if (response.status >= 200 && response.status <= 300) {
      yield put({
        type: GET_RESTAURANT_MENUS_SUCCESS,
        payload: { restaurantMenus: response.data, restaurantLoading: false },
      });
    }
  } catch (error) {
    return {
      type: SET_RESTAURANT_MENUITEM,
      payload: { restaurantError: error.message },
    };
    // yield put({
    //   type: SET_RESTAURANT_MENUITEM,
    //   payload: { restaurantError: error.message },
    // });
  } finally {
    yield put({
      type: SET_RESTAURANT_LOADING,
      payload: { restaurantLoading: false },
    });
  }
}

export function* restaurantSaga() {
  yield takeEvery(GET_RESTAURANT, getRestaurantsSaga);
  yield takeEvery(GET_BUILDING, getBuildingSaga);
  yield takeEvery(GET_RESTAURANT_BY_ID, getRestaurantByIdSaga);
  yield takeEvery(GET_RESTAURANT_MENU, getRestaurantMenuSaga);
  yield takeEvery(GET_RESTAURANT_REVIEW, getRestaurantReviewSaga);
  yield takeEvery(GET_RESTAURANT_MENUS, getRestaurantMenu);
  yield takeEvery(SET_RESTAURANT_MENUITEM, addRestaurantMenuItem);
  yield takeEvery(UPDATE_RESTAURANT_MENUITEM, updateRestaurantMenuItem);
  yield takeEvery(DELETE_RESTAURANT_MENUITEM, deleteRestaurantMenuItem);
}
