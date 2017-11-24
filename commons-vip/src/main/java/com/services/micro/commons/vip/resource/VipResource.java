package com.services.micro.commons.vip.resource;

import com.services.micro.commons.vip.api.VipResponse;
import com.services.micro.commons.vip.config.VipConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@ConditionalOnProperty(name = "service.vip.enabled")
public class VipResource {
    private static VipResponse vipResponse = new VipResponse();

    @RequestMapping("/vipStatus")
    public VipResponse getStatus(Optional<Integer> local, Optional<Integer> global) {
        if (local.isPresent() && global.isPresent()) {
            if (local.get() == 0 && global.get() == 1) {
                throw new RuntimeException("Local cannot be set to down when global is up  ");
            }
        }
        int localParamValue = local.orElse(getIntStatus(vipResponse.getLocal()));
        int globalParamValue = global.orElse(getIntStatus(vipResponse.getGlobal()));
        if (localParamValue == 0 && globalParamValue == 1) {
            throw new RuntimeException("Local cannot be set to down when global is up  ");
        }
        vipResponse.setLocal(getStringStatus(localParamValue));
        vipResponse.setGlobal(getStringStatus(globalParamValue));

        return vipResponse;
    }

    private String getStringStatus(int status) {
        return status == 0 ? VipConstants.DOWN : VipConstants.UP;
    }

    private int getIntStatus(String status) {
        return status != null && status.equals(VipConstants.DOWN) ? 0 : 1;
    }

    @RequestMapping("/vipGlobalStatus")
    public ResponseEntity<String> getGlobalStatus() {
        return getStringResponseEntity(vipResponse.getGlobal());
    }

    @RequestMapping("/vipLocalStatus")
    public ResponseEntity<String> getLocalStatus() {
        return getStringResponseEntity(vipResponse.getLocal());

    }

    private ResponseEntity<String> getStringResponseEntity(String status) {
        if(status.equals(VipConstants.DOWN)) {
            return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>(status, HttpStatus.OK);
    }


}
