import React, { useEffect, useRef, useMemo, useState } from "react";
import { FlatList, TouchableOpacity, TextInput } from "react-native";
import { debounce } from "lodash";
import RestaurantCard from "../src/Components/RestaurantCard";
import BuildingSearchItem from "../src/Components/Search/BuildingSearchItem";
import tw from "twrnc";
const restaurants = [
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
    building: "Building 1",
    rating: 4.5,
    bestSeller: "Cold drinks",
    avgpreptime: "15 mins",
    image: require("../assets/images/placeholder.png"),
  },
  {
    id: 9,
    name: "Restaurant I",
    building: "Building 1",
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

const SearchScreen = ({ navigation }) => {
  const [search, setSearch] = useState("");
  const restaurantMap = useRef(new Map());
  const buildingMap = useRef(new Map());
  const combinedList = useRef([]);

  const debouncedSearch = useRef(debounce(setSearch, 250)).current;

  useEffect(() => {
    restaurants.forEach((res) => {
      restaurantMap.current.set(res.name, res);
      const resList = buildingMap.current.get(res.building) || [];
      resList.push(res);
      buildingMap.current.set(res.building, resList);
    });
    const buildingList = Array.from(buildingMap.current.keys());
    const restaurantList = Array.from(restaurantMap.current.keys());
    const maxLen = Math.max(buildingList.length, restaurantList.length);
    combinedList.current = [];
    for (let i = 0; i < maxLen; i++) {
      if (buildingList[i]) {
        combinedList.current.push({
          type: "building",
          name: buildingList[i],
        });
      }
      if (restaurantList[i]) {
        combinedList.current.push({
          type: "restaurant",
          name: restaurantList[i],
        });
      }
    }
  }, []);

  const searchList = useMemo(() => {
    if (!search) {
      return [];
    }
    let search_results = combinedList.current
      .filter((item) => {
        // Filter results by doing case insensitive match on name here
        return item.name.toLowerCase().includes(search.toLowerCase());
      })
      .sort((a, b) => {
        // Sort results by matching name with keyword position in name
        if (
          a.name.toLowerCase().indexOf(search.toLowerCase()) >
          b.name.toLowerCase().indexOf(search.toLowerCase())
        ) {
          return 1;
        } else if (
          a.name.toLowerCase().indexOf(search.toLowerCase()) <
          b.name.toLowerCase().indexOf(search.toLowerCase())
        ) {
          return -1;
        } else {
          if (a.name > b.name) return 1;
          else return -1;
        }
      });
    return search_results;
  }, [search]);

  return (
    <>
      <TextInput
        style={tw`grow bg-yellow-500 rounded-lg p-2 h-11`}
        placeholder="Search restaurant or building"
        keyboardType="default"
        autoFocus
        onChangeText={debouncedSearch}
      />
      <FlatList
        style={style.flatlistConatiner}
        contentContainerStyle={style.scrollViewContent}
        data={searchList}
        renderItem={({ item }) => (
          <TouchableOpacity
            onPress={() => {
              navigation.navigate("Home", {
                search: item,
              });
            }}
          >
            {item.type === "restaurant" && (
              <RestaurantCard
                key={`restaurant-${item.name}`}
                item={restaurantMap.current.get(item.name)}
              />
            )}
            {item.type === "building" && (
              <BuildingSearchItem
                name={item.name}
                key={`building-${item.name}`}
                restaurants={buildingMap.current.get(item.name)}
              />
            )}
          </TouchableOpacity>
        )}
      />
    </>
  );
};
const style = {
  flatlistConatiner: {
    padding: 5,
  },
  scrollViewContent: {
    padding: 10,
  },
};
export default SearchScreen;
