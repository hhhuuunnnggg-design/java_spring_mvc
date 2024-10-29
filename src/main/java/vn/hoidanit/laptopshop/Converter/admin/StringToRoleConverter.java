package vn.hoidanit.laptopshop.Converter.admin;

import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import vn.hoidanit.laptopshop.repository.RoleRepository;

@Component
public class StringToRoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleRepository roleRepository; // Inject RoleRepository để tìm kiếm Role từ database

    @Override
    public Role convert(String source) {
        // Chuyển đổi chuỗi roleId từ form thành kiểu Long
        Long roleId = Long.valueOf(source);

        // Tìm kiếm Role theo roleId trong cơ sở dữ liệu và trả về
        return (Role) roleRepository.findById(roleId).orElse(null); // Nếu không tìm thấy, trả về null
    }
}
