import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import Restaurants from "../../screens/Restaurants";
import RestaurantDetails from "../../screens/RestaurantDetails";
import FilterScreen from "../../screens/FilterScreen";
import SearchScreen from "../../screens/SearchScreen";
import { useSelector, useDispatch } from "react-redux";
import { TouchableOpacity } from "react-native";
import tw from "twrnc";
import IconTextBar from "../Layouts/IconTextBar";
import { setSelectedBuildingRedux } from "../../redux/actions/RestaurantAction";

const Stack = createStackNavigator();

const RestaurantStack = ({ navigation }) => {
  const screenOptions = {
    headerStyle: {
      backgroundColor: "#EAB308",
    },
  };
  const buildingIds = useSelector(
    (store) => store.restaurant.selectedBuildings
  );
  const dispatch = useDispatch();
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen
        name="Restaurants"
        options={{
          headerLeft: () => (
            <TouchableOpacity
              style={tw`mt-2 bg-yellow-500 rounded-lg py-2 px-6`}
              onPress={() => navigation.navigate("FilterRestaurant")}
            >
              <IconTextBar iconType="Fai" iconName="filter" iconOnly={true} />
            </TouchableOpacity>
          ),
          headerRight: () => {
            return (
              buildingIds.length > 0 && (
                <TouchableOpacity
                  style={tw`mt-2 rounded-lg mr-3 `}
                  onPress={() => dispatch(setSelectedBuildingRedux([]))}
                >
                  <IconTextBar
                    iconType="Ati"
                    iconColor="black"
                    iconName="close"
                    iconOnly={true}
                  />
                </TouchableOpacity>
              )
            );
          },
        }}
        component={Restaurants}
      />
      <Stack.Screen
        name="RestaurantDetail"
        options={({ route }) => ({ title: route.params.title })}
        component={RestaurantDetails}
      />
      <Stack.Screen name="FilterRestaurant" component={FilterScreen} />
      <Stack.Screen name="SearchRestaurant" component={SearchScreen} />
    </Stack.Navigator>
  );
};

export default RestaurantStack;
