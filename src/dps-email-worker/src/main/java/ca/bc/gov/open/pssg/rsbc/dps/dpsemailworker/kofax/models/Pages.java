package ca.bc.gov.open.pssg.rsbc.dps.dpsemailworker.kofax.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Pages")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pages {

    @XmlElement(name = "Page")
    private List<Page> pages = new ArrayList<>();

    protected Pages() { }

    public List<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        this.pages.add(page);
    }
}
