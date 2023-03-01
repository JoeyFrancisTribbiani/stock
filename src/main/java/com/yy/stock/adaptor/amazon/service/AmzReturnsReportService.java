package com.yy.stock.adaptor.amazon.service;

import com.yy.stock.adaptor.amazon.dto.AmzReturnsReportDTO;
import com.yy.stock.adaptor.amazon.entity.AmzReturnsReport;
import com.yy.stock.adaptor.amazon.repository.AmzReturnsReportRepository;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportQueryVO;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportUpdateVO;
import com.yy.stock.adaptor.amazon.vo.AmzReturnsReportVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AmzReturnsReportService {

    @Autowired
    private AmzReturnsReportRepository amzReturnsReportRepository;

    public String save(AmzReturnsReportVO vO) {
        AmzReturnsReport bean = new AmzReturnsReport();
        BeanUtils.copyProperties(vO, bean);
        bean = amzReturnsReportRepository.save(bean);
        return bean.getAmzReturnsReportUPK().toString();
    }

    public void delete(String id) {
        amzReturnsReportRepository.deleteById(id);
    }

    public void update(String id, AmzReturnsReportUpdateVO vO) {
        AmzReturnsReport bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        amzReturnsReportRepository.save(bean);
    }

    public AmzReturnsReportDTO getById(String id) {
        AmzReturnsReport original = requireOne(id);
        return toDTO(original);
    }

    public Page<AmzReturnsReportDTO> query(AmzReturnsReportQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private AmzReturnsReportDTO toDTO(AmzReturnsReport original) {
        AmzReturnsReportDTO bean = new AmzReturnsReportDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private AmzReturnsReport requireOne(String id) {
        return amzReturnsReportRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
