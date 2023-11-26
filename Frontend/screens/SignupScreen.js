import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  View,
  KeyboardAvoidingView,
  Keyboard,
  Text,
  TextInput,
  Pressable,
  TouchableWithoutFeedback,
  ScrollView,
  Switch
} from "react-native";
import tw from "twrnc";
import { emailRegex, usernameRegex, passwordRegex } from "../src/Utils/Regex";
import { setError, signUp } from "../redux/actions/Authentication";
import Colors from '../src/Utils/Colors';

const SignupScreen = () => {
  const dispatch = useDispatch();
  const error = useSelector((store) => store.authentication.error);
  const [disabled, setDisabled] = useState(true);
  const [signUpFormData, setSignUpFormData] = useState({
    username: "",
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
    role: "user", // Keeping it constant - Change while Demo
  });

  const [errors, setValidationErrors] = useState({
    username: "",
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleSignUpState = (field, value) => {
    setSignUpFormData({
      ...signUpFormData,
      [field]: value,
    });
  };

  const validateCredentials = () => {
    const { username, name, email, password, confirmPassword, role } = signUpFormData;
    const isError = { ...errors };

    if (role == "user" && !usernameRegex.test(username)) {
      isError.username = "Invalid B00 Id";
    } else if (role == "restaurant" && !emailRegex.test(username)) {
      isError.username = "Please enter a valid email";
    }

    if (role == "user" && !emailRegex.test(email)) isError.email = "Please enter a valid email";

    if (name.trim() === "") isError.name = "Please enter a valid name";

    if (!passwordRegex.test(password))
      isError.password =
        "Password must have 8 characters, one lowecase uppercase letter number and special character:";

    if (password !== confirmPassword)
      isError.confirmPassword = "Password Mismatch";

    setValidationErrors(isError);
    console.log(isError, "-----> ", errors);
    return Object.values(isError).some(
      (value) => typeof value === "string" && value.trim() !== ""
    );
  };

  const handleSignup = async () => {
    if (validateCredentials()) {
      setTimeout(() => setValidationErrors({}), 4000);
      return;
    }
    // Dispatch the Reducer Action
    let payload = { ...signUpFormData };
    delete payload["confirmPassword"];
    dispatch(signUp(payload));
  };

  useEffect(() => {
    Object.values(signUpFormData).some(
      (value) => typeof value === "string" && value.trim() !== ""
    )
      ? setDisabled(false)
      : setDisabled(true);
  }, [signUpFormData]);

  useEffect(() => {
    error &&
      setTimeout(
        () => dispatch(setError({ error: null, errorType: null })),
        5000
      );
  }, [error]);

  const handleRoleSwitchValueChange = (value) => {
    setSignUpFormData({
      ...signUpFormData,
      role: value === true ? "restaurant" : "user",
    });
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
          <Text style={tw`text-black text-4xl font-bold mb-8`}>Sign Up</Text>
          <Text style={tw`text-red-600 mb-1`}> {error}</Text>
          
          <View style={{ flexDirection: 'row' }} >
            <Text style={tw`text-black text-sm my-3 mr-1`}>User</Text>
            <Switch
              trackColor={{ false: Colors.primaryButton, true: Colors.secondaryButton }}
              thumbColor={signUpFormData.role == "user" ? Colors.primaryButton : Colors.secondaryButton}
              ios_backgroundColor="#3e3e3e"
              onValueChange={handleRoleSwitchValueChange}
              value={signUpFormData.role == "restaurant"}
            />
            <Text style={tw`text-black text-sm my-3`}>Restaurant</Text>
          </View>
          
          <Text style={tw`text-red-600 mb-1`}> {errors.name}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder={ signUpFormData.role == "user" ? "Name" : "Restaurant Owner Name"}
            value={signUpFormData.name}
            onChangeText={(text) => handleSignUpState("name", text)}
          />

          <Text style={tw`text-red-600 mb-1`}>{errors.username}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder={ signUpFormData.role == "user" ? "Banner Id" : "Email"}
            value={signUpFormData.username}
            onChangeText={(text) => handleSignUpState("username", text)}
          />

          {signUpFormData.role == "user" && (
            <>
              <Text style={tw`text-red-600 mb-1`}>{errors.email}</Text>
              <TextInput
                style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
                placeholder="Email"
                value={signUpFormData.email}
                onChangeText={(text) => handleSignUpState("email", text)}
              />
            </>)
          }

          <Text style={tw`text-red-600 mb-1`}>{errors.password}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-1`}
            placeholder="Password"
            secureTextEntry
            value={signUpFormData.password}
            onChangeText={(text) => handleSignUpState("password", text)}
          />
          
          <Text style={tw`text-red-600 mb-1`}>{errors.confirmPassword}</Text>
          <TextInput
            style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-2`}
            placeholder="Confirm Password"
            secureTextEntry
            value={signUpFormData.confirmPassword}
            onChangeText={(text) => handleSignUpState("confirmPassword", text)}
          />
        </ScrollView>
      </TouchableWithoutFeedback>
      <Pressable
        style={[
          tw`bg-yellow-500 rounded-lg py-2 px-4 my-3`,
          disabled ? tw`opacity-50` : tw`opacity-100`,
        ]}
        onPress={handleSignup}
        onPressOut={Keyboard.dismiss}
        disabled={disabled}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Sign Up</Text>
      </Pressable>
    </KeyboardAvoidingView>
  );
};

export default SignupScreen;
