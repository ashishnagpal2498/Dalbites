import React from "react";
import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import tw from "twrnc";
import { useNavigation } from "@react-navigation/native";
import IconTextBar from "../../Layouts/IconTextBar";

const ImageCards = ({ restaurants }) => {
  const res = restaurants.slice(0, restaurants.length > 4 ? 3 : undefined);
  const height = res.length < 3 ? "103%" : "49%";
  const width = res.length < 2 ? "94%" : "45.5%";
  return (
    <View style={{ flex: 4, flexDirection: "row", flexWrap: "wrap" }}>
      {res.map((item) => (
        <View
          style={{
            backgroundColor: "#000",
            height,
            width,
            marginTop: 8,
            marginLeft: 10,
            borderRadius: 5,
            overflow: "hidden",
          }}
          key={item.name}
        >
          <Image
            resizeMode="repeat"
            style={tw`bg-white h-full`}
            source={item.image}
          />
        </View>
      ))}
    </View>
  );
};

const BuildingSearchItem = ({ name, restaurants }) => {
  const navigation = useNavigation();
  return (
    <TouchableOpacity
      onPress={() =>
        navigation.navigate("Restaurants", {
          search: {
            type: "building",
            name,
          },
        })
      }
    >
      <View style={styles.card}>
        <View style={styles.imageConatainer}>
          <View style={tw`h-full`}>
            <ImageCards restaurants={restaurants} />
          </View>
        </View>
        <View style={styles.moreDetailsContainer}>
          <Text style={styles.bestSellerText}>{name}</Text>
          <View style={styles.viewAll}>
            <View style={styles.res}>
              <Text>View all restaurants</Text>
            </View>
            <IconTextBar
              iconName="arrow-forward"
              iconType="Ini"
              iconOnly={true}
              iconProps={{
                style: {
                  fontSize: 18,
                },
              }}
            />
          </View>
        </View>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  card: {
    width: "100%",
    height: 190,
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    marginBottom: 10,
  },
  imageConatainer: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
    backgroundColor: "#fff",
  },
  image: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  moreDetailsContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
    flexDirection: "row",
    justifyContent: "space-between",
    paddingLeft: 10,
    paddingRight: 10,
    paddingBottom: 4,
  },
  bestSellerText: {
    fontSize: 14,
    color: "#000",
  },
  viewAll: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
});

export default BuildingSearchItem;
