import React, { useState, useEffect } from "react";
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  Image,
  FlatList,
} from "react-native";
import { ScrollView } from "react-native-gesture-handler";
import * as ImagePicker from "expo-image-picker";
import { useDispatch, useSelector } from "react-redux";
import Loading from "./Loading";
import { SafeAreaView } from "react-native-safe-area-context";
import { logout } from "../redux/actions/Authentication";
import Reviews from "./Reviews";
import { getAllUserReviews, getUserDetails } from "../redux/actions/UserAction";
import {
  getAllRestaurantReviews,
  getRestaurantById,
} from "../redux/actions/RestaurantAction";

const RestaurantProfile = () => {
  const dispatch = useDispatch();
  const token = useSelector((store) => store.authentication.token);
  const loading = useSelector((store) => store.restaurant.restaurantLoading);
  const restaurant = useSelector((store) => store.restaurant.restaurant);
  const restaurantId = useSelector(
    (store) => store.authentication.restaurantId
  );
  const restaurantReviews = useSelector(
    (store) => store.restaurant.restaurantReviews
  );
  const handleLogout = () => dispatch(logout());

  const [userPhoto, setUserPhoto] = useState(
    require("../assets/images/Dummy_profile_photo.png")
  );

  useEffect(() => {
    console.log("RestaurantId- ", restaurantId);
    dispatch(getAllRestaurantReviews({ id: restaurantId, token }));
    dispatch(getRestaurantById({ token, id: restaurantId }));
  }, []);

  if (loading) {
    return <Loading />;
  }
  const onRefresh = () => {
    dispatch(getAllRestaurantReviews({ id, token }));
    dispatch(getRestaurantById({ token, id: restaurantId }));
  };

  return (
    <View>
      <View style={styles.imageConatainer}>
        <Image
          style={styles.image}
          source={{ uri: restaurant.restaurantImage }}
        />
      </View>
      <View>
        <View style={styles.profileinfo}>
          <Text style={styles.nametext}>
            Restaurant Name : {restaurant?.name}{" "}
          </Text>
          <Text style={styles.emailtext}>
            Email : {restaurant?.loginDao?.username}
          </Text>

          <View style={styles.profilebuttonscontainer}>
            <TouchableOpacity style={styles.button} onPress={handleLogout}>
              <Text style={styles.buttonText}>Logout</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
      <View style={styles.header}>
        <View style={styles.extrahleader}>
          <Text style={styles.headertext}>Reviews</Text>
        </View>
      </View>
      {restaurantReviews.length > 0 ? (
        <FlatList
          data={restaurantReviews}
          refreshing={loading}
          onRefresh={onRefresh}
          //   keyExtractor={(item) => item.reviewId.toString()}
          renderItem={({ item }) => (
            <View style={styles.reviewCard}>
              <Text style={styles.reviewName}>User name not fetched</Text>
              <Text style={styles.reviewRating}>Rating: {item.rating}</Text>
              <Text style={styles.reviewText}>{item.reviewComment}</Text>
            </View>
          )}
        />
      ) : (
        <View>
          <Text style={styles.TabText}>No restaurant reviews</Text>
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    backgroundColor: "lightgrey",
  },
  header: {
    backgroundColor: "black",
    elevation: 5,
    width: "100%",
    alignItems: "center",
  },
  headertext: {
    color: "white",
    fontSize: 25,
    fontWeight: "bold",
    textAlign: "center",
    margin: 5,
  },
  profilebody: {
    width: "100%",
    backgroundColor: "white",
    padding: 10,
  },
  profilephotoContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    borderRightWidth: 1,
  },
  photoContainer: {
    width: "100%",
    aspectRatio: 1,
    overflow: "hidden",
  },
  photo: {
    width: "100%",
    height: "100%",
  },
  photobutton: {
    backgroundColor: "#EAB308",
    paddingVertical: 2,
    paddingHorizontal: 5,
    borderRadius: 5,
    margin: 5,
  },
  phototext: {
    fontSize: 10,
    alignSelf: "center",
  },
  profileinfo: {
    paddingBottom: 7,
    alignSelf: "center",
  },
  nametext: {
    fontSize: 20,
    fontWeight: "bold",
    textAlign: "left",
  },
  bannertext: {
    fontSize: 15,
    textAlign: "left",
  },
  emailtext: {
    fontSize: 15,
    textAlign: "left",
  },
  profilebuttonscontainer: {
    flexDirection: "row",
    justifyContent: "space-evenly",
  },
  button: {
    backgroundColor: "#EAB308",
    paddingVertical: 7,
    paddingHorizontal: 15,
    borderRadius: 10,
    marginVertical: 10,
  },
  buttonText: {
    fontSize: 18,
    fontWeight: "bold",
    textAlign: "center",
  },
  ordercontainer: {
    padding: 5,
  },
  ordercard: {
    backgroundColor: "white",
    width: "95%",
    alignSelf: "center",
    padding: 10,
    marginVertical: 5,
    borderRadius: 10,
    elevation: 20,
    shadowColor: "black",
  },
  row1: {
    flexDirection: "row",
    justifyContent: "space-between",
    backgroundColor: "black",
  },
  row1text: {
    fontSize: 16,
    color: "white",
    padding: 3,
    paddingHorizontal: 7,
  },
  row2: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  row2text: {
    fontSize: 20,
    fontWeight: "bold",
    color: "black",
    padding: 3,
  },
  row3: {
    flexDirection: "row",
  },
  row3text: {
    fontSize: 16,
  },
  addreviewbuttoncontainer: {
    marginTop: 10,
    alignSelf: "center",
  },
  reviewButton: {
    backgroundColor: "#EAB308",
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 7,
  },
  reviewtext: {
    fontSize: 15,
    fontWeight: "bold",
    textAlign: "center",
  },
  imageConatainer: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  image: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  textContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 10,
    paddingVertical: 5,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  restaurantName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingHorizontal: 10,
    color: "#ecc94b",
    // backgroundColor: "rgba(0, 0, 0, 0.5)",
    textAlign: "left",
  },
  rowContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 10,
    paddingBottom: 5,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  buildingName: {
    fontSize: 16,
    color: "#FFFFFF",
    textAlign: "center",
  },
  reviewCard: {
    width: "95%",
    alignSelf: "center",
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    margin: 5,
    padding: 11,
  },
  reviewName: {
    fontSize: 16,
    fontWeight: "bold",
  },
  reviewDate: {
    fontSize: 14,
  },
  reviewRating: {
    fontSize: 14,
  },
  reviewText: {
    fontSize: 14,
  },
});

export default RestaurantProfile;
