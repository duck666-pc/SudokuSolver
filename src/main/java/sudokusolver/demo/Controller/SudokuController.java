package sudokusolver.demo.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sudokusolver.demo.Model.SudokuBoard;
import sudokusolver.demo.Model.SudokuResult;
import sudokusolver.demo.Service.SudokuService;

import java.util.List;
import java.util.Map;

@Controller
public class SudokuController {

    @Autowired
    private SudokuService sudokuService;

    @GetMapping("/")
    public String index() {
        return "views";
    }

    @PostMapping("/")
    public String solve(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        SudokuBoard board = new SudokuBoard();
        int[][] originalBoard = new int[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String val = params.get("cell_" + r + "_" + c);
                int num = (val == null || val.isBlank()) ? 0 : Integer.parseInt(val.trim());
                board.setCells(r, c, num);
                originalBoard[r][c] = num;
            }
        }

        SudokuResult result = sudokuService.solve(board);

        if (!result.isValid()) {
            model.addAttribute("error", result.getErrorMessage());
            model.addAttribute("board", originalBoard);
            model.addAttribute("originalBoard", originalBoard);
            return "views";
        }

        List<int[][]> solutions = result.getSolutions();

        // Lưu vào session để /solve và /back dùng lại
        session.setAttribute("solutions", solutions);
        session.setAttribute("originalBoard", originalBoard);
        session.setAttribute("solutionCount", solutions.size());

        return "redirect:/solve";
    }

    @GetMapping("/solve")
    public String solveResult(@RequestParam(defaultValue = "0") int page,
                              Model model, HttpSession session) {
        List<int[][]> solutions = (List<int[][]>) session.getAttribute("solutions");
        if (solutions == null) return "redirect:/";

        int[][] originalBoard = (int[][]) session.getAttribute("originalBoard");
        int solutionCount = (int) session.getAttribute("solutionCount");

        // Giới hạn page không vượt biên
        page = Math.max(0, Math.min(page, solutionCount - 1));

        model.addAttribute("board", solutions.get(page));
        model.addAttribute("originalBoard", originalBoard);
        model.addAttribute("solutionCount", solutionCount);
        model.addAttribute("page", page);
        return "solve";
    }

    // Quay lại trang nhập với đề bài gốc được phục hồi từ session
    @GetMapping("/back")
    public String back(Model model, HttpSession session) {
        int[][] originalBoard = (int[][]) session.getAttribute("originalBoard");
        if (originalBoard == null) return "redirect:/";

        model.addAttribute("board", originalBoard);
        model.addAttribute("originalBoard", originalBoard);
        return "views";
    }
}