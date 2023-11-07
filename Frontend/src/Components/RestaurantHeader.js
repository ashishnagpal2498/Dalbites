import React from "react";
import { Text, TextInput, TouchableOpacity, View } from "react-native";
import tw from "twrnc";
import { useRoute } from "@react-navigation/native";
import IconTextBar from "../Layouts/IconTextBar";

const getIcon = (screenName) => {
  switch (screenName) {
    case "SearchScreen":
      return {
        type: "Ini",
        name: "arrow-back",
      };
    default:
      return {
        type: "Fai",
        name: "filter",
      };
  }
};
const getIconButtonNavigationScreen = (screenName) => {
  switch (screenName) {
    case "SearchScreen":
      return "Restaurants";
    default:
      return "FilterScreen";
  }
};
const RestaurantHeader = ({ navigation, onSearchChange }) => {
  const route = useRoute();
  const handleSearchPress = () => {
    navigation.navigate("SearchScreen");
  };
  const icon = getIcon(route.name);
  return (
    <View style={styles.header}>
      {
        <TouchableOpacity
          style={tw`grow bg-yellow-500 rounded-lg  h-11 justify-center items-start pl-2`}
          onPress={handleSearchPress}
        >
          <Text style={{ opacity: 0.6 }}>Search restaurant or building</Text>
        </TouchableOpacity>
      }

      {route.name === "SearchScreen" && (
        <TextInput
          style={[tw`bg-yellow-500 rounded-lg p-2 h-4`]}
          placeholder="Search restaurant or building"
          keyboardType="default"
          autoFocus
          onChangeText={onSearchChange}
        />
      )}
    </View>
  );
};

const styles = {
  header: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 10,
    backgroundColor: "#fff",
    elevation: 5,
    width: "100%",
  },
  searchButtonContent: {
    flexDirection: "row",
    alignItems: "center",
  },
  searchIcon: {
    marginRight: 10,
  },
};

export default RestaurantHeader;
