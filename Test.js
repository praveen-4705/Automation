/* global describe expect beforeAll test */
import axios from 'axios';
import * as dotenv from 'dotenv';
import awaitWrapper from '../../src/utilities/awaitwrapper';
import { URL } from 'apollo-env';

var aws4 = require('aws4');
var AWS = require('aws-sdk/global');
var apigClientFactory = require('aws-api-gateway-client').default;

let token: any;

beforeAll(async (done) => {
    dotenv.config();

    let params = {
        IdentityPoolId: process.env.OFA_IDENTITY_POOL_ID
    };
    
    AWS.config.region = process.env.OFA_AWS_REGION;
    AWS.config.credentials = new AWS.CognitoIdentityCredentials(params);
    let cognitoidentity = new AWS.CognitoIdentity();
    AWS.config.credentials.expiryWindow = 15;

    await AWS.config.credentials.get(async function(err) {
        if (err) {
            console.log("Error: "+err);
            return;
        }
         
        let params = {
            IdentityId: AWS.config.credentials.identityId
        };
    
        await cognitoidentity.getCredentialsForIdentity(params, function(err, data) {
            if (err) {
                console.log(err, err.stack); 
            }
            else {

                token = {
                    "accessKeyId": data.Credentials.AccessKeyId,
                    "secretAccessKey": data.Credentials.SecretKey,
                    "sessionToken": data.Credentials.SessionToken
                };  
            };  
            done();  
        });         
    });
});

const testTimeout = 30000;

function filterByModelYear(list, modelSalesYear) {
    const filtered = list.filter(trim => trim.modelSalesYear === modelSalesYear);
    return filtered.length ? filtered[0] : null;
}

function filterByModelName(list, modelName) {
    const filtered = list.filter(trim => trim.modelName === modelName);
    return filtered.length ? filtered[0] : null;
}

function filterSubTrimByName(list, subTrimName) {
    const filtered = list.filter(trim => trim.subTrimName === subTrimName);
    return filtered.length ? filtered[0] : null;
}

function getSignedRequest(payload) {

    var url = new URL(process.env.OFA_VEHICLE_GRAPHQL_URL);

    let request = {
        host: url.hostname,
        method: 'POST',
        url: process.env.OFA_VEHICLE_GRAPHQL_URL,
        data: {"query": payload}, 
        body: JSON.stringify({"query": payload}), 
        path: url.pathname,
        headers: {
            'content-type': 'application/json'
        },
        service:'execute-api',
        region: process.env.OFA_AWS_REGION
    };

    let signedRequest = aws4.sign(request,
    {
        secretAccessKey: token.secretAccessKey,
        accessKeyId: token.accessKeyId,
        sessionToken: token.sessionToken
    });

    delete signedRequest.headers['Host'];
    delete signedRequest.headers['Content-Length'];

    return signedRequest;
}

describe('GetModel: Verify modelInformation is retrieved successfully by vehicleGraphql', () => {
    test('Verify model query', async () => {
        console.log(process.env.OFA_VEHICLE_GRAPHQL_URL);

        var payload = `query Model {
            GetModel(brand:VW,country:USA,modelName:"Jetta"){
                data{
                  modelName,
                  modelTrims(modelSalesYears:["2018"]){
                    data{
                      modelName,
                      modelSalesYear,
                      basePrice,
                    }
                  }
                }
              }
            }`;

        let signedRequest = getSignedRequest(payload);

        const { err, data } = await awaitWrapper(axios(signedRequest));

        expect(err).toBeNull();
        const Model = data.data.data.GetModel.data;
        expect(Model.modelName).toBe("Jetta");
        expect(Model.modelTrims.data.length).toBe(1);
    }, testTimeout);

    test('GetModel: Verify model query response contains modeltrims that are filtered as per the request params', async () => {
        console.log(process.env.OFA_VEHICLE_GRAPHQL_URL);

        var payload = `query Model {
            GetModel(brand:VW,country:USA,modelName:"Jetta"){
                data{
                  modelName,
                  modelTrims(modelSalesYears:["2018"]){
                    data{
                      modelName,
                      modelSalesYear,
                      basePrice,
                    }
                  }
                }
              }
            }
            `;

        let signedRequest = getSignedRequest(payload);

        const { err, data } = await awaitWrapper(axios(signedRequest));

        expect(err).toBeNull();
        const Model = data.data.data.GetModel.data;
        expect(Model.modelName).toBe("Jetta");
        const modelTrims = Model.modelTrims.data;
        expect(modelTrims.length).toBe(1);
        expect(modelTrims[0].modelName).toBe("Jetta");
        expect(modelTrims[0].modelSalesYear).toBe("2018");
        expect(modelTrims[0].basePrice).not.toBeNull();
    }, testTimeout);

    test('GetModel: Verify model query response contains correct number of modelYear objects', async () => {
        console.log(process.env.OFA_VEHICLE_GRAPHQL_URL);
        
        var payload = `query Model {
            GetModel(brand:VW,country:USA,modelName:"Jetta"){
                data{
                  modelName,
                  modelTrims(modelSalesYears:["2018","2019"]){
                    data{
                      modelName,
                      modelSalesYear,
                      basePrice,
                    }
                  }
                }
              }
            }
            `;

        let signedRequest = getSignedRequest(payload);

        const { err, data } = await awaitWrapper(axios(signedRequest));

        expect(err).toBeNull();
        const Model = data.data.data.GetModel.data;
        expect(Model.modelName).toBe("Jetta");
        const modelTrims = Model.modelTrims.data;

        const trims2018 = filterByModelYear(modelTrims, "2018");
        expect(trims2018.modelSalesYear).toBe("2018");
        expect(trims2018.modelName).toBe("Jetta");
        expect(trims2018.basePrice).not.toBeNull();

        const trims2019 = filterByModelYear(modelTrims, "2019");
        expect(trims2019.modelSalesYear).toBe("2019");
        expect(trims2019.modelName).toBe("Jetta");
        expect(trims2019.basePrice).not.toBeNull();

        const trims2017 = filterByModelYear(modelTrims, "2017");
        expect(trims2017).toBeNull();
    }, testTimeout);


    test('GetModel: Verify model subtrims retieved for Jetta 2018', async () => {
        console.log(process.env.OFA_VEHICLE_GRAPHQL_URL);

        var payload = `query Model {
            GetModel(brand:VW,country:USA,modelName:"Jetta"){
                data{
                  modelName,
                  modelTrims(modelSalesYears:["2018"]){
                    data{
                      modelName,
                      modelSalesYear,
                      basePrice,

                        subTrims(subTrimIDs: []) {
                            data {
                            subTrimId,
                            subTrimName,
                            subTrimDescription,
                            basePrice
                            }
                        }
                    }
                  }
                }
              }
            }
            `;

        let signedRequest = getSignedRequest(payload);

        const { err, data } = await awaitWrapper(axios(signedRequest));

        expect(err).toBeNull();
        const Model = data.data.data.GetModel.data;
        expect(Model.modelName).toBe("Jetta");
        const modelTrims = Model.modelTrims.data;
        expect(modelTrims.length).toBe(1);

        const trims2018 = filterByModelYear(modelTrims, "2018");
        expect(trims2018.modelSalesYear).toBe("2018");
        expect(trims2018.modelName).toBe("Jetta");
        expect(trims2018.basePrice).not.toBeNull();

        const subtrims = trims2018.subTrims.data;
        expect(subtrims.length).toBe(6);
        expect(filterSubTrimByName(subtrims, "S").subTrimName).toBe("S");
        expect(filterSubTrimByName(subtrims, "S").basePrice).toBe("18645");

        expect(filterSubTrimByName(subtrims, "SE").subTrimName).toBe("SE");
        expect(filterSubTrimByName(subtrims, "SE").basePrice).toBe("21245");

        expect(filterSubTrimByName(subtrims, "Wolfsburg Edition").subTrimName).toBe("Wolfsburg Edition");
        expect(filterSubTrimByName(subtrims, "Wolfsburg Edition").basePrice).toBe("20345");

        expect(filterSubTrimByName(subtrims, "SE Sport").subTrimName).toBe("SE Sport");
        expect(filterSubTrimByName(subtrims, "SE Sport").basePrice).toBe("23245");

        expect(filterSubTrimByName(subtrims, "GLI").subTrimName).toBe("GLI");
        expect(filterSubTrimByName(subtrims, "GLI").basePrice).toBe("29545");

    }, testTimeout);
})
