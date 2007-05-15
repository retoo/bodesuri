#!/usr/bin/ruby

require 'date'

VALID_TIME = /^\d\d:\d\d$/
def convert_to_hours(time)
  # time is 12:34
  unless time.match VALID_TIME
    raise "Invalid time #{time}"
  end
  
  hour, minute = time.split(":").map{|s| s.to_i }
  
  return hour + (minute.to_f / 60)
end

while time = gets
  puts convert_to_hours(time)
end
  

NON_NUMMERIC = /\D/

weeks = Hash.new(0)

STDIN.each do |line|
  (blurb, week_s, date_s, hours_s, desc) = line.split("||").collect{|e| e.strip }
  next if week_s == 'Woche'
  
  if hours_s.match NON_NUMMERIC
    records = hours_s.split
    
    time = 0
    
    records.each do |record| 
      # 12:00-13:30
      times = record.split("-");
      
      if times.length != 2
        raise "Ungültiges Zeitformat"
      end
      
      times.map{|time| convert_to_hours }
    end
  end
    

  week = week_s.to_i
  hours = hours_s.to_f
  
  weeks[week] += hours
  # puts "#{week} #{date_s} #{hours} #{desc}"  
end
puts 
puts "Statistik: (Update #{Date.today})"
puts

puts "|| Woche || Total ||"
weeks.sort.each do |week, hours|
  puts "|| #{week} ||  #{hours} ||"
end

total = weeks.inject(0) {|sum, e| sum + e.last}
puts
puts "Total: #{total}"
