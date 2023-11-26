import React, { useEffect } from "react";
import { View, Text, StyleSheet } from "react-native";
import { ScrollView } from "react-native-gesture-handler";
import { useDispatch, useSelector } from "react-redux";
import Loading from "./Loading";
import { SafeAreaView } from "react-native-safe-area-context";

const Reviews = ({ userReviews }) => {
  const token = useSelector((store) => store.authentication.token);
  const loading = useSelector((store) => store.user.profileLoading);

  if (loading) {
    return <Loading />;
  }
  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <View style={styles.extrahleader}>
          <Text style={styles.headertext}>Reviews</Text>
        </View>
      </View>
      <View style={styles.ordercontainer}>
        {userReviews.map((review, index) => (
          <View style={styles.ordercard} key={index}>
            <View style={styles.row1}>
              <Text style={styles.row1text}>
                Restaurant Name: {review.restaurantName}
              </Text>
            </View>

            <View style={styles.row2}>
              <Text style={styles.row2text}>Rating {review.rating}</Text>
            </View>

            <View style={styles.row3}>
              <Text style={styles.row3text}>Comment: </Text>
              <Text style={styles.row3text}>{review.reviewComment}</Text>
            </View>
            <View style={styles.row3}>
              <Text style={styles.row3text}></Text>
              <Text style={styles.row3text}>
                Submitted on:{" "}
                {new Date(review.createdAt).toLocaleDateString("en-US")}
              </Text>
            </View>
          </View>
        ))}
      </View>
    </ScrollView>
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
    alignSelf: "center",
    backgroundColor: "white",
    padding: 10,
  },
  profileinfocontainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    borderWidth: 1,
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
    flex: 3,
    paddingHorizontal: 7,
    paddingBottom: 7,
    justifyContent: "space-evenly",
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
    borderWidth: 1,
    borderTopWidth: 0,
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
});

export default Reviews;
