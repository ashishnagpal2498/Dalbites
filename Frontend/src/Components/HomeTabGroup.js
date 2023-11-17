import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import IconTextBar from "../Layouts/IconTextBar";
import Home from "../../screens/Home";
import Food from "../../screens/Food";
import Restaurants from "../../screens/Restaurants";
import Notifications from "../../screens/Notifications";
import RestaurantStack from "./RestaurantStack";

const Tab = createBottomTabNavigator();

const HomeTabGroup = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      headerShown: false,
      tabBarIcon: ({ focused, color }) => {
        let iconName;
        if (route.name === "Home") {
          iconType = "Ini";
          iconName = focused ? "home" : "home-outline";
        } else if (route.name === "Food") {
          iconType = "Mci";
          iconName = "food";
        } else if (route.name === "RestaurantsStack") {
          iconType = "Ini";
          iconName = "restaurant";
        } else if (route.name === "Notifications") {
          iconType = "Ini";
          iconName = focused
            ? "notifications-circle"
            : "notifications-circle-outline";
        }

        return (
          <IconTextBar
            iconName={iconName}
            iconType={iconType}
            iconOnly={true}
            color={color}
          />
        );
      },
      tabBarActiveTintColor: "tomato",
      tabBarInactiveTintColor: "gray",
    })}
  >
    <Tab.Screen name="Home" component={Home} />
    <Tab.Screen name="Food" component={Food} />
    <Tab.Screen
      name="RestaurantsStack"
      options={{ title: "Restaurants" }}
      component={RestaurantStack}
    />
    <Tab.Screen name="Notifications" component={Notifications} />
  </Tab.Navigator>
);

export default HomeTabGroup;
