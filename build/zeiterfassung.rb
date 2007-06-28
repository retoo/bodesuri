#!/usr/bin/ruby

require 'date'

VALID_TIME = /^\d\d:\d\d$/
TIME_FORMAT = /-/

def convert_to_hours(time)
  # time is 12:34
  unless time.match VALID_TIME
    raise "Invalid time #{time}"
  end
  
  hour, minute = time.split(":").map{|s| s.to_i }
  
  return hour + (minute.to_f / 60)
end



weeks = Hash.new {|h, k| h[k] = Hash.new(0)}

STDIN.each do |line|
  begin
    if line.match /END/
      break
    end
    
    (blurb, week_s, date_s, hours_s, desc) = line.split("||").collect{|e| e.strip }
    if week_s.match /Woche/
      puts line
      next
    end
    
    time = 0.0
    
    if hours_s.match TIME_FORMAT
      records = hours_s.split(/,/)
      
      records.map{|r| r.strip }.each do |record| 
        # 12:00-13:30
        times = record.split(/\s*-\s*/);
        
        if times.length != 2
          raise "Ungültiges Zeitformat #{record}"
        end
        
        from, to = times.map{|t| convert_to_hours(t) }

        time += (to - from)
      end
    else
      time += hours_s.to_f
    end
  rescue => e
    puts line
    raise e
  end

  week = week_s.to_i
  
  weeks[week][desc] += time
  puts line
end

puts 
puts "Statistik: (Update #{Date.today})"
puts

stats_week = Hash.new(0)

puts "|| Woche || Modul || Total ||"
weeks.sort.each do |week, modules|
  hours_total = 0
  
  modules.each do |modul, hours|
    puts "|| #{week} || #{modul}|| #{hours} ||"
    hours_total += hours
  end
  
  stats_week[week] += hours_total
end

puts 
puts "|| Woche || Total ||"
stats_week.sort.each do |week, hours|
  puts "|| #{week} ||  #{hours} ||"
end


#total = weeks.inject(0) {|sum, e| sum + e.last}
# puts
# puts "Total: #{total}"
