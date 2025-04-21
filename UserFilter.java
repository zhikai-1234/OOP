import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class UserFilter {
    private String locationFilter = "";
    private Set<String> flatTypeFilters = new HashSet<>();
    private double minPrice = 0.0;
    private double maxPrice = Double.MAX_VALUE; 
    private LocalDate applicationDateFilter = null;

    public String getLocationFilter() {
    	return locationFilter; 
    }
    
    public Set<String> getFlatTypeFilters() { 
    	return flatTypeFilters; 
    }
    
    public double getMinPrice() { 
    	return minPrice; 
    }
    
    public double getMaxPrice() { 
    	return maxPrice;
    }
    
    public LocalDate getApplicationDateFilter() {
    	return applicationDateFilter; 
    }

    public void setLocationFilter(String locationFilter) {
    	this.locationFilter = locationFilter; 
    }
    
    public void setFlatTypeFilters(Set<String> flatTypeFilters) {
    	this.flatTypeFilters = flatTypeFilters; 
    }
    
    public void setMinPrice(double minPrice) { 
    	this.minPrice = minPrice; 
    }
    
    public void setMaxPrice(double maxPrice) { 
    	this.maxPrice = maxPrice; 
    }
    
    public void setApplicationDateFilter(LocalDate applicationDateFilter) { 
    	this.applicationDateFilter = applicationDateFilter; 
    }

    public List<TemplateProject> filterProjects(List<TemplateProject> projects) {
        List<TemplateProject> result = new ArrayList<TemplateProject>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        for (TemplateProject p : projects) {
            if (!p.getVisibility()) continue;

            boolean matchesLocation = locationFilter.isEmpty() || p.getNeighbourhood().equalsIgnoreCase(locationFilter);

            boolean matchesFlatType = false;
            if (flatTypeFilters.isEmpty()) {
                matchesFlatType = true;
            } else {
                if (flatTypeFilters.contains(p.getType1()) && p.getNumOfType1() > 0) matchesFlatType = true;
                if (flatTypeFilters.contains(p.getType2()) && p.getNumOfType2() > 0) matchesFlatType = true;
            }

            boolean matchesPrice = false;
            if (flatTypeFilters.isEmpty()) {
                matchesPrice = (p.getType1Price() >= minPrice && p.getType1Price() <= maxPrice) ||
                               (p.getType2Price() >= minPrice && p.getType2Price() <= maxPrice);
            } else {
                if (flatTypeFilters.contains(p.getType1()) && p.getNumOfType1() > 0 &&
                    (p.getType1Price() >= minPrice && p.getType1Price() <= maxPrice)) {
                    matchesPrice = true;
                }
                if (flatTypeFilters.contains(p.getType2()) && p.getNumOfType2() > 0 &&
                    (p.getType2Price() >= minPrice && p.getType2Price() <= maxPrice)) {
                    matchesPrice = true;
                }
            }

            boolean matchesDate = true;
            if (applicationDateFilter != null) {
                try {
                    LocalDate open = LocalDate.parse(p.getOpenDate(), formatter);
                    LocalDate close = LocalDate.parse(p.getCloseDate(), formatter);
                    matchesDate = !applicationDateFilter.isBefore(open) && !applicationDateFilter.isAfter(close);
                } catch (Exception e) {
                    matchesDate = false;
                }
            }

            if (matchesLocation && matchesFlatType && matchesPrice && matchesDate) {
                result.add(p);
            }
        }

        result.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        return result;
    }

    public void promptForFilters(String userKey, User user, List<TemplateProject> allProjects) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter location filter (leave blank for all): ");
        this.setLocationFilter(sc.nextLine().trim());

        int eligibility = 0;
        if (user instanceof Applicant) {
            eligibility = ((Applicant) user).getEligibilityStatus();
        } else {
            eligibility = 2;
        }

        System.out.println("Select flat types to filter by:");
        System.out.println("[1] 2-Room only");
        
        if (eligibility == 2) {
            System.out.println("[2] 3-Room only");
            System.out.println("[3] Both 2-Room and 3-Room");
        }
        
        System.out.println("[0] No flat type filter");

        int choice = sc.nextInt();
        sc.nextLine();

        Set<String> flatTypes = new HashSet<String>();
        
        if (choice == 1) {
            flatTypes.add("2-Room");
        } 
        
        else if (choice == 2 && eligibility == 2) {
            flatTypes.add("3-Room");
        } 
        
        else if (choice == 3 && eligibility == 2) {
            flatTypes.add("2-Room");
            flatTypes.add("3-Room");
        }
        
        this.setFlatTypeFilters(flatTypes);

        System.out.print("Enter minimum price (leave blank for 0): ");
        String minInput = sc.nextLine().trim();
        this.setMinPrice(minInput.isEmpty() ? 0.0 : Double.parseDouble(minInput));

        System.out.print("Enter maximum price (leave blank for no max): ");
        String maxInput = sc.nextLine().trim();
        this.setMaxPrice(maxInput.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxInput));

        System.out.print("Enter application date to filter (dd/MM/yyyy) or leave blank for all: ");
        String dateInput = sc.nextLine().trim();
        if (dateInput.isEmpty()) {
            this.setApplicationDateFilter(null);
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate date = LocalDate.parse(dateInput, formatter);
                this.setApplicationDateFilter(date);
            } catch (Exception e) {
                System.out.println("Invalid date format. Ignoring date filter.");
                this.setApplicationDateFilter(null);
            }
        }

        System.out.println("\nFilters updated: " + this);
    }

    public String toString() {
        return "[Location: " + (locationFilter.isEmpty() ? "All" : locationFilter)
             + ", Flat Types: " + (flatTypeFilters.isEmpty() ? "All" : flatTypeFilters)
             + ", Price: $" + minPrice + " to $" + maxPrice
             + ", Application Date: " + (applicationDateFilter == null ? "Any" : applicationDateFilter) + "]";
    }
}