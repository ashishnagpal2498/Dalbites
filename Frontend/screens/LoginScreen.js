import React from 'react';
import { View, Text, TextInput, TouchableOpacity } from 'react-native';
import tw from 'twrnc';

const LoginScreen = () => {
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');

  const handleLogin = () => {
    // Handle login logic here
  };

  return (
    <View style={tw`flex-1 justify-center items-center bg-white`}>
      <Text style={tw`text-black text-4xl font-bold mb-8`}>Login</Text>
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
      <TouchableOpacity
        style={tw`bg-yellow-500 rounded-lg py-2 px-4`}
        onPress={handleLogin}
      >
        <Text style={tw`text-black text-lg font-semibold`}>Login</Text>
      </TouchableOpacity>
    </View>
  );
};

export default LoginScreen;
