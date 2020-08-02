import React, { useState } from 'react'
import { View, Text, Image} from 'react-native';
import purposeStyles from './style/PurposeStyle';


export default function PurposeOtherWrite({ purpose }) {

    const [purposeScope, setPurposeScope] = useState();
    const [purposeGroup, setPurposeGroup] = useState();

    const goalImage = './../../../../../../asset/sample_Image/Sam.png'

    const goalTotal = '23'

    return (
        <View style={purposeStyles.container}>
            <View style={purposeStyles.headContainer}>
                <Text style={purposeStyles.title}>
                    좋은 목표 계획이군요!
                    {"\n"}
                    나머지 세부 설정만 남았습니다
                </Text>
            </View>
            <View >
                <View style={{ textAlign: 'center', justifyContent: 'center', }}>
                    <Text style={{ marginTop: '10%', marginLeft: 20, fontSize: 18 }}>공개 범위</Text>
                </View>

                <View style={{ borderWidth: 1, width: 130, height: 35, alignItems: 'center', justifyContent: 'center', marginLeft: '5%', marginTop: '2%' }}>
                    <Text style={{ fontSize: 17, }}>공개</Text></View>

                <View style={{ textAlign: 'center', justifyContent: 'center', }}>
                    <Text style={{ marginTop: '3%', marginLeft: 20, fontSize: 18 }}>그룹  <Text style={{ color: '#BEBEBE', fontSize: 25, fontWeight: 'bold', }}>?</Text></Text>
                </View>

                <View style={{ backgroundColor: 'blue', width: '90%', height: '33%', alignSelf: 'center', marginTop: 10 }}>
                    <Image source={require(goalImage)}
                        style={{
                            width: '100%', height: '100%'
                        }}>
                    </Image>
                </View>

                <Text style={{ fontSize: 18, marginLeft: 20, marginTop: 8 }}>하루 OO 시간 수행하기</Text>

                <View style={{ textAlign: 'center', justifyContent: 'center', }}>
                    <Text style={{ marginTop: '3%', marginLeft: 20, fontSize: 16, marginRight: 100 }}> 100개의 목표 저장
        <Text style={{ color: '#838383', fontSize: 16, }}> 총 {goalTotal}개 완료</Text></Text>
                </View>


            </View>

        </View>
    );
}