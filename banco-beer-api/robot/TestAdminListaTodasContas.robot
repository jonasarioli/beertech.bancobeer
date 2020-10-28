*** Settings ***
Library    RequestsLibrary

*** Variables ***
${HOST}    https://beertech-banco.herokuapp.com/beercoins


*** Test Cases ***
001_ListaTodasContas
	# Arrange
	create session    get_accounts    ${HOST}
	${headers}=    Create Dictionary   Authorization=Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiUGVyZmlsIjoiUk9MRV9BRE1JTiIsIlNhbGRvIjowLjAwLCJOb21lIjoiQWRtaW4iLCJIYXNoIjoiZDcxMjBmYzUzMGVkNjA5ZDQwNjRjMTNiNmM4Yzg4ZDMiLCJleHAiOjE2MDM5MjU0OTUsImlhdCI6MTYwMzgzOTA5NX0.sBb7sJNXPRON9mjPYa4t8L0Cz0HbgVPwlQphuFQbTiU

    ${page}=    Create Dictionary      page=0
    ${size}=    Create Dictionary      page=10

	# Act
	${response}=    get request    get_accounts    /conta?${page}&${size}  headers=${headers}

	# Assert
	log to console    ${response.json()}
	Should be equal as strings    ${response.status_code}    200
