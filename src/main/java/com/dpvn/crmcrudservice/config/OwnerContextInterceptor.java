package com.dpvn.crmcrudservice.config;

import com.dpvn.sharedcore.async.UserContext;
import com.dpvn.sharedcore.domain.constant.Globals;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
class OwnerContextInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) {
    String sellerId = request.getHeader(Globals.Headers.SELLER_HEADER);
    String retailer = request.getHeader(Globals.Headers.RETAILER_HEADER);
    String warehouseId = request.getHeader(Globals.Headers.WAREHOUSE_HEADER);
    if (sellerId != null) {
      UserContext.setCurrentSeller(Long.parseLong(sellerId));
    }
    if (retailer != null) {
      UserContext.setCurrentRetailer(retailer);
    }
    if (warehouseId != null) {
      UserContext.setCurrentWarehouse(Long.parseLong(warehouseId));
    }

    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    UserContext.clear(); // cleanup tránh leak
  }
}
