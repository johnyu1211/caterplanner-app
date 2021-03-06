import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native'
import DecimalDayWidget from '../../../atom/icon/DecimalDayWidget';


export default function Card({ id,  image, title, date, onPress }) {
    return (
        <TouchableOpacity style={{
            height: '100%', justifyContent: 'center'
        }} activeOpacity={1}>
            <View style={{
                borderTopRightRadius: 45,
                borderTopLeftRadius: 45,
                borderBottomStartRadius: 40,
                borderBottomEndRadius: 40,
                backgroundColor: '#ffffff',
                elevation: 3,
                height: 380,
                margin: 3,
            }}
            >
                <View style={{
                    flex: 2,
                    borderWidth: 5,
                    borderColor: 'white',
                    borderTopRightRadius: 45,
                    borderTopLeftRadius: 45,
                }}>
                    <Image
                        source={{ uri: image }}
                        style={{
                            flex: 1, width: "100%", height: undefined, borderTopRightRadius: 40,
                            borderTopLeftRadius: 40,
                        }}
                    />
                    <View style={{ position: 'absolute', top: -40, width: '100%', alignItems:'center'}}>
                        <Image
                            resizeMode="stretch"
                            style={{ width: '90%', height: 80, tintColor: '#585858' }}
                            source={require('../../../../../../asset/image/card_header.png')}
                        />
                    </View>
                </View>
                <View style={{
                    flex: 1
                }}>
                    <View style={{ flex: 1, justifyContent: 'center' }}>
                        <Text
                            adjustsFontSizeToFit
                            numberOfLines={1}
                            style={{
                                alignSelf: 'center',
                                fontSize: 20
                            }}>
                            {title}
                        </Text>
                    </View>
                    <View style={{ flex: 1, justifyContent: 'flex-start' }}>
                        <DecimalDayWidget stat={0} decimalDay={date}/>
                    </View>
                </View>
            </View>
        </TouchableOpacity>
    )
}


const styles = StyleSheet.create({

    container: {
        //flex:1,
        backgroundColor: 'white',
        height: '100%',
        borderTopRightRadius: 40,
        borderTopLeftRadius: 40,

        borderWidth: 1,


        borderBottomStartRadius: 40,
        borderBottomEndRadius: 40

    },

    image_con: {
        borderTopRightRadius: 40,
        borderTopLeftRadius: 40,
        backgroundColor: 'aqua',
        height: '77%',
        width: '100%'
    },

    title_con: {

        alignSelf: 'center',
        marginTop: '5%',


    },

    title: {
        fontSize: 40,

    },

    date: {
        marginTop: '2%',
        color: 'red',
        alignSelf: 'center',
        fontSize: 18
    },

    more: {
        width: '100%',
        height: 40,

        marginTop: '1%',

        alignItems: 'center',
        alignSelf: 'center',
        justifyContent: 'center',

    }




})