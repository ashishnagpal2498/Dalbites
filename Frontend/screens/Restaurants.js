import React from "react";
import { View, Text, Image, TouchableOpacity, FlatList } from "react-native";
import tw from "twrnc";
import Icon from "react-native-vector-icons/FontAwesome";
import RestaurantCard from "../src/Components/RestaurantCard";
import IconTextBar from "../src/Layouts/IconTextBar";


const restaurantData = [
  {
    id: 1,
    name: "Restaurant A",
    building: "Building 1",
    rating: 4.5,
    bestSeller: "coffee",
    avgpreptime: "20 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 2,
    name: "Restaurant B",
    building: "Building 2",
    rating: 4.4,
    bestSeller: "Tea",
    avgpreptime: "25 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 3,
    name: "Restaurant C",
    building: "Building 3",
    rating: 4.3,
    bestSeller: "Sub",
    avgpreptime: "30 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 4,
    name: "Restaurant D",
    building: "Building 4",
    rating: 4.2,
    bestSeller: "pizza",
    avgpreptime: "35 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 5,
    name: "Restaurant E",
    building: "Building 5",
    rating: 4.1,
    bestSeller: "burger",
    avgpreptime: "40 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 6,
    name: "Restaurant F",
    building: "Building 1",
    rating: 3.9,
    bestSeller: "Indian",
    avgpreptime: "45 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 7,
    name: "Restaurant G",
    building: "Building 2",
    rating: 4.4,
    bestSeller: "chinese",
    avgpreptime: "10 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 8,
    name: "Restaurant H",
    building: "Building 3",
    rating: 4.5,
    bestSeller: "Cold drinks",
    avgpreptime: "15 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 9,
    name: "Restaurant I",
    building: "Building 4",
    rating: 3.5,
    bestSeller: "Franky",
    avgpreptime: "50 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 10,
    name: "Restaurant J",
    building: "Building 5",
    rating: 4.5,
    bestSeller: "Pizza",
    avgpreptime: "20 mins",
    image: require("../assets/images/placeholder.png"),
  },
];


const handleSearchPress = () => {
  navigation.navigate("SearchScreen");
  //Code to get the output of SearchScreen
};


// const handleCardPress = (restaurantcard) => {
//   navigation.navigate("RestaurantDetailsScreen");
// };


const Restaurants = ({ navigation }) => {
  return (
    <View style={{ flex: 1 }}>
      <View style={styles.header}>
        <TouchableOpacity
          style={tw`mt-2 bg-yellow-500 rounded-lg py-2 px-6`}
          onPress={() => navigation.navigate("FilterScreen")}
        >
            <IconTextBar
              iconType= "Fai"
              iconName="filter"
              iconOnly = {true}
            />
        </TouchableOpacity>


        <TouchableOpacity
          style={tw`mt-2 bg-yellow-500 rounded-lg py-2 px-29`}
          onPress={handleSearchPress}
        >
          <View style={styles.searchButtonContent}>
            <IconTextBar
              iconType= "Fai"
              iconName="search"
              iconOnly = {true}
            />
            <Text style={tw`text-black text-lg font-semibold`}>
              {/* Building name */}
              </Text>
          </View>
        </TouchableOpacity>
      </View>


      <FlatList style={styles.flatlistConatiner}
        data={restaurantData}
        renderItem={RestaurantCard}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.scrollViewContent}
      />
    </View>
  );
};

const styles = {
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 10,
    backgroundColor: "#fff",
    elevation: 5,
    paddingTop: 0,
    paddingBottom: 10,
   
  },
  searchButtonContent: {
    flexDirection: "row",
    alignItems: "center",
  },
  searchIcon: {
    marginRight: 10,
  },
  flatlistConatiner:{
    padding: 5
  },
 
  scrollViewContent: {
    padding: 10,
  },
};


export default Restaurants;