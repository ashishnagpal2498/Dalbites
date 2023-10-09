import React from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import tw from 'twrnc';

const SignupScreen = () => {
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');
  const [confirmPassword, setConfirmPassword] = React.useState('');

  const handleSignup = () => {
    // Handle signup logic here
  };

  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      <Text style={tw`text-black text-4xl font-bold mb-8`}>Sign Up</Text>
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
      />
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Password"
        secureTextEntry
        value={password}
        onChangeText={setPassword}
      />
      <TextInput
        style={tw`bg-gray-200 rounded-lg py-2 px-4 w-80 mb-4`}
        placeholder="Confirm Password"
        secureTextEntry
        value={confirmPassword}
        onChangeText={setConfirmPassword}
      />
      <TouchableOpacity
        style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={handleSignup}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Sign Up</Text>
      </TouchableOpacity>
    </View>
  );
};

export default SignupScreen;
