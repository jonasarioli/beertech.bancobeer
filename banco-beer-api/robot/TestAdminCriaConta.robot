*** Settings ***
Library    RequestsLibrary
Library    JSONLibrary

*** Variables ***
${HOST}    https://beertech-banco.herokuapp.com/beercoins

*** Test Cases ***
001_Admin_Realiza_Login
#Como admin
	# Arrange

	create session    beertech_banco    ${HOST}
	${headers}=    Create Dictionary    Content-Type=application/json
	&{data}=    Create Dictionary    email=admin@email.com    password=grupocolorado

#Realizo login
	# Act
	${response}=    Post request    beertech_banco    /login    headers=${headers}    json=${data}

#Recebo um token de autenticação
	# Assert
	Should be equal as strings    ${response.status_code}    200
	log to console    ${response.json()}

	${ADMIN_TOKEN}=    Get Value From Json    ${response.json()}    $..token
	Set Global Variable    ${ADMIN_TOKEN}

002_Admin_Cria_Conta
#
    log to console    ${ADMIN_TOKEN}
    ${headers}=    Create Dictionary    Content-Type=application/json     Authorization=Bearer ${ADMIN_TOKEN}
    &{data}=       Create Dictionary    cnpj=1234567    email=teste@email.com    nome=testrobot     senha=123456
    ${response}=    Post request    beertech_banco    /conta    headers=${headers}    json=${data}
    log to console    ${response.json()}
    Should be equal as strings    ${response.status_code}    201