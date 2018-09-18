@echo off

SET OUT=".\output"

@echo TESTCASE:[1]: test calculator with known input and expected-output
SET KNOWN_INPUT="%~dp0\input"
SET EXPECTED_OUT="%~dp0\expected-out"
IF NOT EXIST %KNOWN_INPUT% ECHO %KNOWN_INPUT% not found
IF NOT EXIST %EXPECTED_OUT% ECHO %KNOWN_INPUT% not found
IF EXIST %OUT% DEL /F %OUT%
@java -jar target\calculator-1.0-SNAPSHOT.jar %KNOWN_INPUT% %OUT%
@type %OUT%
@FC %OUT% %EXPECTED_OUT%

@echo TESTCASE:[2]: test calculator with cyclic dependency input and expected-output
SET KNOWN_INPUT="%~dp0\crc-input"
SET EXPECTED_OUT="%~dp0\crc-expected-out"
IF NOT EXIST %KNOWN_INPUT% ECHO %KNOWN_INPUT% not found
IF NOT EXIST %EXPECTED_OUT% ECHO %KNOWN_INPUT% not found
IF EXIST %OUT% DEL /F %OUT%
@java -jar target\calculator-1.0-SNAPSHOT.jar %KNOWN_INPUT% %OUT%
@type %OUT%
@FC %OUT% %EXPECTED_OUT%

@echo TESTCASE:[3]: test calculator with negative value input and expected-output
SET KNOWN_INPUT="%~dp0\negative-value-input"
SET EXPECTED_OUT="%~dp0\negative-value-expected-out"
IF NOT EXIST %KNOWN_INPUT% ECHO %KNOWN_INPUT% not found
IF NOT EXIST %EXPECTED_OUT% ECHO %KNOWN_INPUT% not found
IF EXIST %OUT% DEL /F %OUT%
@java -jar target\calculator-1.0-SNAPSHOT.jar %KNOWN_INPUT% %OUT%
@type %OUT%
@FC %OUT% %EXPECTED_OUT%

@echo TESTCASE:[4]: test calculator with extension-ops input and expected-output
SET KNOWN_INPUT="%~dp0\extension-ops-input"
SET EXPECTED_OUT="%~dp0extension-ops-expected-out"
IF NOT EXIST %KNOWN_INPUT% ECHO %KNOWN_INPUT% not found
IF NOT EXIST %EXPECTED_OUT% ECHO %KNOWN_INPUT% not found
IF EXIST %OUT% DEL /F %OUT%
@java -jar target\calculator-1.0-SNAPSHOT.jar %KNOWN_INPUT% %OUT%
@type %OUT%
@FC %OUT% %EXPECTED_OUT%