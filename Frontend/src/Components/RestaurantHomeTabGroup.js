import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import IconTextBar from "../Layouts/IconTextBar";
import AddMenu from "../../screens/AddMenu";

const Tab = createBottomTabNavigator();

const RestaurantHomeTabGroup = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      headerShown: false,
      tabBarIcon: ({ focused, color }) => {
        let iconName;
        if (route.name === "Menu") {
          iconType = "Ini";
          iconName = focused ? "home" : "home-outline";
        } else if (route.name === "Order") {
          iconType = "Mci";
          iconName = "food";
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
    <Tab.Screen name="Menu" component={AddMenu} />
    {/* <Tab.Screen name="Order" component={Food} /> --> Restaurant Orders Screen Here*/}
  </Tab.Navigator>
);

export default RestaurantHomeTabGroup;
