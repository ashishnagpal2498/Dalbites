import React, { useEffect, useState } from "react";
import {
  View,
  FlatList,
  ActivityIndicator,
  TouchableOpacity,
  Text,
} from "react-native";
import RestaurantCard from "../src/Components/RestaurantCard";
import { useDispatch, useSelector } from "react-redux";
import {
  getRestaurants,
  setSelectedBuildingRedux,
} from "../redux/actions/RestaurantAction";
import tw from "twrnc";
import IconTextBar from "../src/Layouts/IconTextBar";
import Loading from "./Loading";

const Restaurants = ({ navigation }) => {
  const buildingIds = useSelector(
    (store) => store.restaurant.selectedBuildings
  );

  const dispatch = useDispatch();
  const restaurants = useSelector((store) => store.restaurant.restaurants);
  const token = useSelector((store) => store.authentication.token);
  const loading = useSelector((store) => store.restaurant.restaurantLoading);

  useEffect(() => {
    console.log("Executed ----> UseEffect Restaurants", buildingIds);
    dispatch(getRestaurants({ id: buildingIds ? buildingIds : [], token }));
  }, [buildingIds]);

  if (loading) {
    return <Loading />;
  }

  const onRefresh = () =>
    dispatch(getRestaurants({ id: buildingIds ? buildingIds : [], token }));

  return (
    <View style={{ flex: 1 }}>
      {/* <View style={styles.header}>
        <TouchableOpacity
          style={tw`mt-2 ml-3 rounded-lg py-2 `}
          onPress={
            buildingIds.length
              ? () => dispatch(setSelectedBuildingRedux([]))
              : () => navigation.navigate("SearchRestaurant")
          }
        >
          <View style={styles.searchButtonContent}>
            <IconTextBar
              iconType={buildingIds.length ? "Ati" : "Fai"}
              iconName={buildingIds.length ? "close" : "search"}
              iconOnly={true}
            />
            <Text style={tw`text-black text-lg font-semibold`}>
              {buildingIds.length ? "Search Results" : "Search here ..."}
            </Text>
          </View>
        </TouchableOpacity>
      </View> */}
      <View style={{ flex: 1 }}>
        <FlatList
          style={styles.flatlistConatiner}
          data={restaurants}
          refreshing={loading}
          onRefresh={onRefresh}
          renderItem={({ item, index }) => (
            <RestaurantCard item={item} index={index} navigation={navigation} />
          )}
          keyExtractor={(item) => item.id.toString()}
          contentContainerStyle={styles.scrollViewContent}
        />
      </View>
    </View>
  );
};

const styles = {
  header: {
    flex: 1,
    maxHeight: 60,
    flexDirection: "row",
    padding: 10,
    backgroundColor: "#fff",
    elevation: 5,
    paddingTop: 0,
    paddingBottom: 10,
  },
  searchButtonContent: {
    flexDirection: "row",
  },
  flatlistConatiner: {
    padding: 5,
  },

  scrollViewContent: {
    padding: 10,
  },
};

export default Restaurants;
