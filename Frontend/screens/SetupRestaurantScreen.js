import React, { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import {
  View,
  KeyboardAvoidingView,
  Keyboard,
  Text,
  TextInput,
  Pressable,
  TouchableWithoutFeedback,
  ScrollView,
  StyleSheet,
  Image
} from "react-native";
import tw from "twrnc";
import { useSelector } from "react-redux";

import { setError, setupRestaurantAccount } from "../redux/actions/Authentication";
import * as ImagePicker from 'expo-image-picker';
import FontAwesome from "@expo/vector-icons/FontAwesome";
import SelectDropdown from 'react-native-select-dropdown'
import axios from "axios";
import { BuildingListAPI } from "../redux/APIs";
import * as SecureStore from "expo-secure-store";

const SetupRestaurantScreen = ({ navigation }) => {
  const dispatch = useDispatch();
  const restaurantId = useSelector(
    (store) => store.authentication.restaurantId
  );
  const error = useSelector((store) => store.authentication.error);
  const [disabled, setDisabled] = useState(true);
  const estimatedDeliveryTimes = ["1 minute", "2 minute", "5 minute", "10 minute", "15 minute", "20 minute", "25 minute", "30 minute"]
  const [setupRestaurantFormData, setSetupRestaurantFormData] = useState({
    restaurantName: "",
    restaurantDescription: "",
    buildingId: "",
    estimatedDeliveryTime: "",
    restaurantImage: ""
  });
  const [buildings, setBuildings] = useState([])

  const [errors, setValidationErrors] = useState({
    restaurantName: "",
    restaurantDescription: "",
    buildingId: "",
    estimatedDeliveryTime: "",
    restaurantImage: ""
  });

  useEffect(() => {
    restaurantId &&
      navigation.navigate('AddMenu', { id: restaurantId })
  }, [restaurantId]);

  const handleSetupRestaurantState = (field, value) => {
    setSetupRestaurantFormData({
      ...setupRestaurantFormData,
      [field]: value,
    });
  };

  const validateCredentials = () => {
    const { restaurantName, buildingId, restaurantDescription, restaurantImage, estimatedDeliveryTime } = setupRestaurantFormData;
    const isError = { ...errors };

    if (restaurantName.trim().length <= 0)
      isError.restaurantName = "Enter restaurant name";

    if (isNaN(buildingId)) isError.buildingId = "Select building";

    if (restaurantDescription.trim() === "") isError.restaurantDescription = "Enter restaurant description";

    setValidationErrors(isError);
    console.log(isError, "-----> ", errors);
    return Object.values(isError).some(
      (value) => typeof value === "string" && value.trim() !== ""
    );
  };

  const handleSetupRestaurant = async () => {
    if (validateCredentials()) {
      setTimeout(() => setValidationErrors({}), 4000);
      return;
    }
    setupRestaurantFormData.authToken = await SecureStore.getItemAsync("token")
    dispatch(setupRestaurantAccount(setupRestaurantFormData));
  };

  const pickImageAsync = async () => {
    let result = await ImagePicker.launchImageLibraryAsync({
      allowsEditing: true,
      quality: 1,
    });

    if (!result.canceled) {
      setSetupRestaurantFormData({
        ...setupRestaurantFormData,
        restaurantImage: result.assets[0]
      })
    } else {
      alert('You did not select any image.');
    }
  };

  useEffect(() => {
    Object.values(setupRestaurantFormData).some(
      (value) => typeof value === "string" && value.trim() !== ""
    )
      ? setDisabled(false)
      : setDisabled(true);
  }, [setupRestaurantFormData]);

  useEffect(() => {
    error &&
      setTimeout(
        () => dispatch(setError({ error: null, errorType: null })),
        5000
      );
  }, [error]);

  useEffect(() => {
    fetchBuildings();
  },[])

  const fetchBuildings = async () => {
    const authToken = await SecureStore.getItemAsync("token")
    const data = await axios.get(BuildingListAPI, {
      headers: {
        Authorization: `Bearer ${authToken}`
      }
    })
    setBuildings(data.data)
  }
  return (
    <KeyboardAvoidingView
      behavior="height"
      style={tw`flex-1 justify-center items-center bg-white`}
    >
      <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
        <ScrollView
          contentContainerStyle={[
            tw`flex-1 justify-center items-center`,
            { minHeight: 500 },
          ]}
        >
          <Text style={tw`text-black text-4xl font-bold mb-8`}>Setup Restaurant</Text>
          <Text style={tw`text-red-600 mb-1`}> {error}</Text>
          <Text style={tw`text-red-600 mb-1`}> {errors.buildingId}</Text>
          <SelectDropdown
            data={buildings.map(e => e.name)}
            defaultButtonText="Select Building"
            buttonStyle={[styles.buttonContainer, tw`bg-gray-200 rounded-lg mb-2 items-start`, { paddingVertical: 12 }]}
            buttonTextStyle={[tw`text-black text-sm text-left`]}
            selectedRowStyle={[tw`bg-yellow-500`]}
            onSelect={(selectedItem, index) => {
              setupRestaurantFormData.buildingId = buildings.find(e => e.name == selectedItem)?.id || null
            }}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.restaurantName}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Restaurant Name"
            value={setupRestaurantFormData.restaurantName}
            onChangeText={(text) => handleSetupRestaurantState("restaurantName", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.restaurantDescription}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Restaurant Description"
            value={setupRestaurantFormData.restaurantDescription}
            onChangeText={(text) => handleSetupRestaurantState("restaurantDescription", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.estimatedDeliveryTime}</Text>
          <SelectDropdown
            data={estimatedDeliveryTimes}
            defaultButtonText="Average Estimated Delivery Time (in minutes)"
            buttonStyle={[styles.buttonContainer, tw`bg-gray-200 text-gray-700 rounded-lg mb-2 items-start`, { paddingVertical: 12 }]}
            buttonTextStyle={[tw`text-black text-sm text-left`]}
            onSelect={(selectedItem, index) => {
              setupRestaurantFormData.estimatedDeliveryTime = selectedItem
            }}
            selectedRowStyle={[tw`bg-yellow-500`]}
          />

          <Pressable
            style={[ styles.buttonContainer,
              tw`bg-gray-200 rounded-lg py-2 px-4 my-4`,
              { display: 'flex'}
            ]}
            onPress={pickImageAsync} >
            <FontAwesome
              name="picture-o"
              size={18}
              style={[styles.buttonIcon, tw`text-black`]}
            />
            <Text style={[styles.buttonLabel, tw`text-black`]}>Choose Photo</Text>
          </Pressable>

          {setupRestaurantFormData.restaurantImage.uri && 
            <Image source={{ uri: setupRestaurantFormData.restaurantImage.uri }} style={{ width: 200, height: 200 }} />
          }
        </ScrollView>
      </TouchableWithoutFeedback>
      <Pressable
        style={[
          tw`bg-yellow-500 rounded-lg py-2 px-4 my-3`,
          disabled ? tw`opacity-50` : tw`opacity-100`,
        ]}
        onPress={handleSetupRestaurant}
        onPressOut={Keyboard.dismiss}
        disabled={disabled}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Setup Account</Text>
      </Pressable>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  buttonContainer: {
    width: '100%'
  },
  buttonIcon: {
    paddingRight: 8,
  },
  buttonLabel: {
    fontSize: 16,
  },
});

export default SetupRestaurantScreen;
