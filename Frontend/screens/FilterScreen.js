import React, { useEffect, useState } from "react";
import { View, TouchableOpacity, Text } from "react-native";
import {
  MultipleSelectList,
  SelectList,
} from "react-native-dropdown-select-list";
import { useDispatch, useSelector } from "react-redux";
import tw from "twrnc";
import {
  getBuildings,
  setSelectedBuildingRedux,
} from "../redux/actions/RestaurantAction";
import Loading from "./Loading";

const Filter = ({ navigation }) => {
  const buildings = useSelector((store) => store.restaurant.buildings);
  const loading = useSelector((store) => store.restaurant.restaurantLoading);
  const dispatch = useDispatch();
  const token = useSelector((store) => store.authentication.token);

  const [selectedBuildings, setSelectedBuildings] = useState([]);
  //   const [sortBy, setSortBy] = useState("");
  const buildingOptions = buildings.map((building) => ({
    value: building.name,
    key: building.id,
  }));
  const handleFilter = () => {
    console.log("SelectedBuilding --- > ", selectedBuildings);
    dispatch(setSelectedBuildingRedux(selectedBuildings));
    navigation.navigate("Restaurants");
  };

  useEffect(() => {
    console.log("Filter screen --> fetch buildings", token);
    dispatch(getBuildings({ token }));
  }, []);
  if (loading) {
    return <Loading />;
  }
  return (
    <View>
      {/* <View style={{width: '80%', marginLeft: '10px'}}> */}
      <View style={tw`px-7`}>
        <MultipleSelectList
          setSelected={(selectedOptions) =>
            setSelectedBuildings(selectedOptions)
          }
          data={buildingOptions}
          save="key"
          boxStyles={{ marginTop: 25 }}
          label="Buildings"
          placeholder="Select Buildings"
          searchPlaceholder="Search"
        />
      </View>
      {/* <View style={tw`px-7 py-4`}>
        <SelectList
          setSelected={(val) => setSortBy(val)}
          data={sortByList}
          save="value"
          label="Sort by"
          placeholder="Sort by"
          searchPlaceholder="Search"
          search={false}
        />
      </View> */}
      <TouchableOpacity
        style={tw`bg-yellow-500 rounded-lg py-2 mt-15 ml-33 w-1/4`}
        onPress={handleFilter}
      >
        <Text style={tw`text-black text-lg font-semibold text-center`}>
          Apply
        </Text>
      </TouchableOpacity>
    </View>
  );
};

export default Filter;
