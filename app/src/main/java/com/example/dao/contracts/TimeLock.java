import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.5.3.
 */
@SuppressWarnings("rawtypes")
public class TimeLock extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CANCELLER_ROLE = "CANCELLER_ROLE";

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_EXECUTOR_ROLE = "EXECUTOR_ROLE";

    public static final String FUNC_PROPOSER_ROLE = "PROPOSER_ROLE";

    public static final String FUNC_CANCEL = "cancel";

    public static final String FUNC_EXECUTE = "execute";

    public static final String FUNC_EXECUTEBATCH = "executeBatch";

    public static final String FUNC_GETMINDELAY = "getMinDelay";

    public static final String FUNC_GETOPERATIONSTATE = "getOperationState";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GETTIMESTAMP = "getTimestamp";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_HASHOPERATION = "hashOperation";

    public static final String FUNC_HASHOPERATIONBATCH = "hashOperationBatch";

    public static final String FUNC_ISOPERATION = "isOperation";

    public static final String FUNC_ISOPERATIONDONE = "isOperationDone";

    public static final String FUNC_ISOPERATIONPENDING = "isOperationPending";

    public static final String FUNC_ISOPERATIONREADY = "isOperationReady";

    public static final String FUNC_ONERC1155BATCHRECEIVED = "onERC1155BatchReceived";

    public static final String FUNC_ONERC1155RECEIVED = "onERC1155Received";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_SCHEDULE = "schedule";

    public static final String FUNC_SCHEDULEBATCH = "scheduleBatch";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_UPDATEDELAY = "updateDelay";

    public static final Event CALLEXECUTED_EVENT = new Event("CallExecuted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event CALLSALT_EVENT = new Event("CallSalt", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event CALLSCHEDULED_EVENT = new Event("CallScheduled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>(true) {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CANCELLED_EVENT = new Event("Cancelled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event MINDELAYCHANGE_EVENT = new Event("MinDelayChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected TimeLock(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TimeLock(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TimeLock(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TimeLock(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<CallExecutedEventResponse> getCallExecutedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CALLEXECUTED_EVENT, transactionReceipt);
        ArrayList<CallExecutedEventResponse> responses = new ArrayList<CallExecutedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CallExecutedEventResponse typedResponse = new CallExecutedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CallExecutedEventResponse getCallExecutedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CALLEXECUTED_EVENT, log);
        CallExecutedEventResponse typedResponse = new CallExecutedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<CallExecutedEventResponse> callExecutedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCallExecutedEventFromLog(log));
    }

    public Flowable<CallExecutedEventResponse> callExecutedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLEXECUTED_EVENT));
        return callExecutedEventFlowable(filter);
    }

    public static List<CallSaltEventResponse> getCallSaltEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CALLSALT_EVENT, transactionReceipt);
        ArrayList<CallSaltEventResponse> responses = new ArrayList<CallSaltEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CallSaltEventResponse typedResponse = new CallSaltEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.salt = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CallSaltEventResponse getCallSaltEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CALLSALT_EVENT, log);
        CallSaltEventResponse typedResponse = new CallSaltEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.salt = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CallSaltEventResponse> callSaltEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCallSaltEventFromLog(log));
    }

    public Flowable<CallSaltEventResponse> callSaltEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLSALT_EVENT));
        return callSaltEventFlowable(filter);
    }

    public static List<CallScheduledEventResponse> getCallScheduledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CALLSCHEDULED_EVENT, transactionReceipt);
        ArrayList<CallScheduledEventResponse> responses = new ArrayList<CallScheduledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CallScheduledEventResponse typedResponse = new CallScheduledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.predecessor = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.delay = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CallScheduledEventResponse getCallScheduledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CALLSCHEDULED_EVENT, log);
        CallScheduledEventResponse typedResponse = new CallScheduledEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.target = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.data = (byte[]) eventValues.getNonIndexedValues().get(2).getValue();
        typedResponse.predecessor = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
        typedResponse.delay = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
        return typedResponse;
    }

    public Flowable<CallScheduledEventResponse> callScheduledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCallScheduledEventFromLog(log));
    }

    public Flowable<CallScheduledEventResponse> callScheduledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CALLSCHEDULED_EVENT));
        return callScheduledEventFlowable(filter);
    }

    public static List<CancelledEventResponse> getCancelledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(CANCELLED_EVENT, transactionReceipt);
        ArrayList<CancelledEventResponse> responses = new ArrayList<CancelledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelledEventResponse typedResponse = new CancelledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static CancelledEventResponse getCancelledEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(CANCELLED_EVENT, log);
        CancelledEventResponse typedResponse = new CancelledEventResponse();
        typedResponse.log = log;
        typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<CancelledEventResponse> cancelledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getCancelledEventFromLog(log));
    }

    public Flowable<CancelledEventResponse> cancelledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCELLED_EVENT));
        return cancelledEventFlowable(filter);
    }

    public static List<MinDelayChangeEventResponse> getMinDelayChangeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(MINDELAYCHANGE_EVENT, transactionReceipt);
        ArrayList<MinDelayChangeEventResponse> responses = new ArrayList<MinDelayChangeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MinDelayChangeEventResponse typedResponse = new MinDelayChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldDuration = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newDuration = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static MinDelayChangeEventResponse getMinDelayChangeEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(MINDELAYCHANGE_EVENT, log);
        MinDelayChangeEventResponse typedResponse = new MinDelayChangeEventResponse();
        typedResponse.log = log;
        typedResponse.oldDuration = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newDuration = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<MinDelayChangeEventResponse> minDelayChangeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getMinDelayChangeEventFromLog(log));
    }

    public Flowable<MinDelayChangeEventResponse> minDelayChangeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MINDELAYCHANGE_EVENT));
        return minDelayChangeEventFlowable(filter);
    }

    public static List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses = new ArrayList<RoleAdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleAdminChangedEventResponse getRoleAdminChangedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEADMINCHANGED_EVENT, log);
        RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleAdminChangedEventFromLog(log));
    }

    public Flowable<RoleAdminChangedEventResponse> roleAdminChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADMINCHANGED_EVENT));
        return roleAdminChangedEventFlowable(filter);
    }

    public static List<RoleGrantedEventResponse> getRoleGrantedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses = new ArrayList<RoleGrantedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleGrantedEventResponse getRoleGrantedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEGRANTED_EVENT, log);
        RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleGrantedEventFromLog(log));
    }

    public Flowable<RoleGrantedEventResponse> roleGrantedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEGRANTED_EVENT));
        return roleGrantedEventFlowable(filter);
    }

    public static List<RoleRevokedEventResponse> getRoleRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses = new ArrayList<RoleRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static RoleRevokedEventResponse getRoleRevokedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ROLEREVOKED_EVENT, log);
        RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
        typedResponse.log = log;
        typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getRoleRevokedEventFromLog(log));
    }

    public Flowable<RoleRevokedEventResponse> roleRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREVOKED_EVENT));
        return roleRevokedEventFlowable(filter);
    }

    public RemoteFunctionCall<byte[]> CANCELLER_ROLE() {
        final Function function = new Function(FUNC_CANCELLER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> DEFAULT_ADMIN_ROLE() {
        final Function function = new Function(FUNC_DEFAULT_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> EXECUTOR_ROLE() {
        final Function function = new Function(FUNC_EXECUTOR_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> PROPOSER_ROLE() {
        final Function function = new Function(FUNC_PROPOSER_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel(byte[] id) {
        final Function function = new Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> execute(String target, BigInteger value, byte[] payload, byte[] predecessor, byte[] salt, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_EXECUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, target), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.DynamicBytes(payload), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> executeBatch(List<String> targets, List<BigInteger> values, List<byte[]> payloads, byte[] predecessor, byte[] salt, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_EXECUTEBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(targets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(payloads, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<BigInteger> getMinDelay() {
        final Function function = new Function(FUNC_GETMINDELAY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getOperationState(byte[] id) {
        final Function function = new Function(FUNC_GETOPERATIONSTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> getRoleAdmin(byte[] role) {
        final Function function = new Function(FUNC_GETROLEADMIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getTimestamp(byte[] id) {
        final Function function = new Function(FUNC_GETTIMESTAMP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> grantRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(byte[] role, String account) {
        final Function function = new Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<byte[]> hashOperation(String target, BigInteger value, byte[] data, byte[] predecessor, byte[] salt) {
        final Function function = new Function(FUNC_HASHOPERATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, target), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.DynamicBytes(data), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<byte[]> hashOperationBatch(List<String> targets, List<BigInteger> values, List<byte[]> payloads, byte[] predecessor, byte[] salt) {
        final Function function = new Function(FUNC_HASHOPERATIONBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(targets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(payloads, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<Boolean> isOperation(byte[] id) {
        final Function function = new Function(FUNC_ISOPERATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isOperationDone(byte[] id) {
        final Function function = new Function(FUNC_ISOPERATIONDONE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isOperationPending(byte[] id) {
        final Function function = new Function(FUNC_ISOPERATIONPENDING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isOperationReady(byte[] id) {
        final Function function = new Function(FUNC_ISOPERATIONREADY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> onERC1155BatchReceived(String param0, String param1, List<BigInteger> param2, List<BigInteger> param3, byte[] param4) {
        final Function function = new Function(
                FUNC_ONERC1155BATCHRECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param2, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(param3, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicBytes(param4)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> onERC1155Received(String param0, String param1, BigInteger param2, BigInteger param3, byte[] param4) {
        final Function function = new Function(
                FUNC_ONERC1155RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Uint256(param2), 
                new org.web3j.abi.datatypes.generated.Uint256(param3), 
                new org.web3j.abi.datatypes.DynamicBytes(param4)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
        final Function function = new Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1), 
                new org.web3j.abi.datatypes.generated.Uint256(param2), 
                new org.web3j.abi.datatypes.DynamicBytes(param3)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceRole(byte[] role, String callerConfirmation) {
        final Function function = new Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, callerConfirmation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(role), 
                new org.web3j.abi.datatypes.Address(160, account)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> schedule(String target, BigInteger value, byte[] data, byte[] predecessor, byte[] salt, BigInteger delay) {
        final Function function = new Function(
                FUNC_SCHEDULE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, target), 
                new org.web3j.abi.datatypes.generated.Uint256(value), 
                new org.web3j.abi.datatypes.DynamicBytes(data), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt), 
                new org.web3j.abi.datatypes.generated.Uint256(delay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> scheduleBatch(List<String> targets, List<BigInteger> values, List<byte[]> payloads, byte[] predecessor, byte[] salt, BigInteger delay) {
        final Function function = new Function(
                FUNC_SCHEDULEBATCH, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(targets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(values, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.DynamicBytes>(
                        org.web3j.abi.datatypes.DynamicBytes.class,
                        org.web3j.abi.Utils.typeMap(payloads, org.web3j.abi.datatypes.DynamicBytes.class)), 
                new org.web3j.abi.datatypes.generated.Bytes32(predecessor), 
                new org.web3j.abi.datatypes.generated.Bytes32(salt), 
                new org.web3j.abi.datatypes.generated.Uint256(delay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateDelay(BigInteger newDelay) {
        final Function function = new Function(
                FUNC_UPDATEDELAY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newDelay)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static TimeLock load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TimeLock(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TimeLock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TimeLock(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TimeLock load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TimeLock(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TimeLock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TimeLock(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class CallExecutedEventResponse extends BaseEventResponse {
        public byte[] id;

        public BigInteger index;

        public String target;

        public BigInteger value;

        public byte[] data;
    }

    public static class CallSaltEventResponse extends BaseEventResponse {
        public byte[] id;

        public byte[] salt;
    }

    public static class CallScheduledEventResponse extends BaseEventResponse {
        public byte[] id;

        public BigInteger index;

        public String target;

        public BigInteger value;

        public byte[] data;

        public byte[] predecessor;

        public BigInteger delay;
    }

    public static class CancelledEventResponse extends BaseEventResponse {
        public byte[] id;
    }

    public static class MinDelayChangeEventResponse extends BaseEventResponse {
        public BigInteger oldDuration;

        public BigInteger newDuration;
    }

    public static class RoleAdminChangedEventResponse extends BaseEventResponse {
        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRevokedEventResponse extends BaseEventResponse {
        public byte[] role;

        public String account;

        public String sender;
    }
}
